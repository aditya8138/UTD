package net;

import core.Message;
import core.MessageType;
import core.Node;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * Created by hanlin on 4/27/17.
 */
public class ConnectionInitiator {
    private String address;
    private int port;
    private char label;
    private Socket socket;

    public ConnectionInitiator(String address, int port, char label) {
        this.address = address;
        this.port = port;
        this.label = label;
        this.socket = null;
    }
    public void connect()
    {
        try {
            socket = new Socket(this.address, this.port);
            System.out.print("\nInitiated connection successfully to " + this.address +
                    " at port " + this.port + "\n> ");
            CommunicationThread communicationThread = new CommunicationThread(this.socket, this.label);
//                server.setName("ThreadTo"+nodeConnectedTo);
            new Thread(communicationThread).start();

            Thread.sleep(500);//to establish conn

            Node.getInstance().getCommunicationThreads().add(communicationThread);


//                Message m = new Message(Message.Type.INIT, LocalDateTime.now(), ID, null);
//                m.setTimeStamp(LocalDateTime.now());
//                m.setSenderNodeID(MyData.getMyData().getMyNodeLabel());
//                m.setMessage(object);

            communicationThread.send(new Message(MessageType.INIT_CONNECTION, LocalDateTime.now(), Node.getInstance().getID(), null));
//                return server;
        } catch (UnknownHostException e) {
            System.err.println("\nUnknownHostException when opening Socket to address, " + this.address +
                    " at port " + this.port + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println("\nIOException when opening Socket to address, " + this.address +
                    " at port " + this.port + ": " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
