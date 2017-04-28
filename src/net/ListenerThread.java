package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import core.*;

/**
 * Created by hanlin on 4/27/17.
 */
public class ListenerThread implements Runnable {
    private int port;
    private ServerSocket serverSocket;

    public ListenerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Accepting at port: " + port);

            while (!Node.getInstance().isShutDown()) {
                socket = this.serverSocket.accept();
                if (socket == null)
                    continue;
                new Thread(new CommunicationThread(socket)).start();
            }
        } catch(SocketException se){
            System.out.println("\nClosing socket at port " + this.port);
        }  catch (IOException e) {
            System.err.println("\nIOException when opening ServerSocket at port " + this.port + ": " + e.getMessage());
        } finally {
            try {
                this.close();
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                System.err.println("\nIOException when closing Socket: " + e.getMessage());
            }
        }
    }

    public void close() {
        try {
            if(this.serverSocket != null)
                this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("\nIOException when closing ServerSocket: " + e.getMessage());}
    }
}