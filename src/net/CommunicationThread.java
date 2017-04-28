package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.*;
import ds.*;
import org.omg.CORBA.NO_IMPLEMENT;

/**
 * Created by hanlin on 4/27/17.
 */
public class CommunicationThread implements Runnable {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private char nodeConnectedTo;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    public CommunicationThread(Socket socket, char nodeConnectedTo) {
        this.socket = socket;
        this.nodeConnectedTo = nodeConnectedTo;
    }

    @Override
    public void run() {
        read();
    }

    public void send(Message message) {
        try {
            if(this.oos != null){
                this.oos.writeObject(message);
                this.oos.flush();
            } else {
                System.out.println("Did not send: " + message + " to "+getNodeConnectedTo());
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

                    System.out.println(message);
                        /* If the message is an initialization msg, */
                    if (message.getMessageType() == MessageType.INIT) {
                        this.nodeConnectedTo = message.getSenderID();
                        Object content = message.getContent();
                        Node.getInstance().getCommunicationThreads().add(this);
//                        if(content instanceof String)
//                            addNodetoNetwork((String)content);
                    } else {
                        Node.getInstance().getMessageQueue().put(message);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Connection closed with " + nodeConnectedTo);
            this.close();
            Node.getInstance().getCommunicationThreads().remove(this);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException when reading object from stream: " + e.getMessage());
        } catch (InterruptedException e) {
            if (!Node.getInstance().isShutDown())
                System.err.println("ServerThread connected to node" + nodeConnectedTo +
                        " interrupted: " + e.getMessage());
        } finally {
//            this.close();
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
//            Node.getInstance().removeNodeFromNetwork(nodeConnectedTo);
        } catch (IOException e) {
            System.err.println("\nIOException when closing Socket/oos/ois: " + e.getMessage());
        }
    }

//    private void addNodetoNetwork(String label) {
//        String[] fields = label.split("\t");
//        if(fields.length != 1)
//            System.err.println("Abnormal initialization message received: " + label);
//        this.nodeConnectedTo = fields[1].charAt(0);
////            this.setName("ThreadTo"+nodeLabel);
//        Node.getInstance().getCommunicationThreads().add(this);
//    }

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
