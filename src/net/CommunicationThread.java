package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import core.*;

public class CommunicationThread implements Runnable {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private char nodeConnectedTo;

    CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    CommunicationThread(Socket socket, char nodeConnectedTo) {
        this.socket = socket;
        this.nodeConnectedTo = nodeConnectedTo;
    }

    @Override
    public void run() {
        read();
    }

    public synchronized void send(Message message) {
        try {
            if(this.oos != null){
                this.oos.writeObject(message);
                this.oos.flush();
            } else {
                System.out.println("Did not send: " + message + " to " + getNodeConnectedTo());
            }
        } catch (IOException e) {
            System.err.println("IOException while sending object(" + message + ") to " +
                    getNodeConnectedTo() + ": " + e.getMessage());
        }
    }

    private void read() {
        try {
            if(this.oos == null)
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            if(this.ois == null)
                this.ois = new ObjectInputStream(this.socket.getInputStream());

            while (!Node.getInstance().isShutDown()) {
                Object object = ois.readObject();
                if(object!= null && object instanceof Message) {
                    Message message = (Message) object;

                    /* If the message is an initialization msg, */
                    if (message.getMessageType() == MessageType.INIT_CONNECTION) {
                        this.nodeConnectedTo = message.getSenderID();
                        Node.getInstance().getCommunicationThreads().add(this);
                    } else {
                        Node.getInstance().getMessageQueue().put(message);
                    }
                }
            }
        } catch (IOException e) {
            System.err.print("\nInfo: Connection closed with " + nodeConnectedTo + "\n> ");
            this.close();
            Node.getInstance().getCommunicationThreads().remove(this);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException when reading object from stream: " + e.getMessage());
        } catch (InterruptedException e) {
            if (!Node.getInstance().isShutDown())
                System.err.println("ServerThread connected to node" + nodeConnectedTo +
                        " interrupted: " + e.getMessage());
        } finally {
            this.close();
        }
    }

    public void close() {
        try {
            if(this.oos != null)
                this.oos.close();
            if(this.ois != null)
                this.ois.close();
            if(this.socket!= null)
                this.socket.close();
        } catch (IOException e) {
            System.err.println("\nIOException when closing Socket/oos/ois: " + e.getMessage());
        }
    }

    public char getNodeConnectedTo() {
        return nodeConnectedTo;
    }

    public String getHostName(){
        return this.socket.getInetAddress().getHostName();
    }

    public int getPort(){
        return this.socket.getPort();
    }
}
