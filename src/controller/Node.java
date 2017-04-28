package controller;

import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

import net.*;
import ds.*;

/**
 * Created by hanlin on 4/26/17.
 */
public class Node {
    private static Node ourInstance = new Node();

    public static Node getInstance() {
        return ourInstance;
    }

    private static final String NETWORK_CONFIG = "NetworkConfig.txt";

    private boolean shutDown;
    private char ID;
    private SynchronousQueue<Message> messageQueue;
    private CopyOnWriteArrayList<CommunicationThread> CommunicationThreads;
    private String networkNodesFile;
    private int port;

    private Node() {
        this.shutDown = false;
        this.messageQueue = new SynchronousQueue<>();
        this.CommunicationThreads = new CopyOnWriteArrayList<>();
        this.port = new Random().nextInt(65535);
    }

    public void setID(char ID) {
        this.ID = ID;
    }

//    public synchronized void removeNodeFromNetwork(char senderID) {
////        synchronized (this.CommunicationThreads) {
//            int mark = -1;
//            for (int i = this.CommunicationThreads.size() - 1; i >= 0; i--) {
//                if(this.CommunicationThreads.get(i).getNodeConnectedTo() == senderID) {
//                    mark = i;
//                    break;
//                }
//            }
//            if(mark != -1) {
//                this.CommunicationThreads.remove(mark);
//            }
////        }
//    }

    public void start() {
        new Thread(new ListenerThread(this.port)).start();
        try {
            String localAddress = InetAddress.getLocalHost().getHostName();

            this.initialConnection();
            this.addEntryToFile();

            System.out.println("\nCurrent Connected Nodes:\n" + Node.getInstance().printCommunicationThreads());

            new CLIController().start();

            this.removeEntryFromFile();
        } catch (UnknownHostException e) {
            System.err.println("\nUnknownHostException when get local hostname: " + e.getMessage());
        }
    }

    public boolean isShutDown() {
        return shutDown;
    }

    public char getID() {
        return ID;
    }

    public SynchronousQueue<Message> getMessageQueue() {
        return messageQueue;
    }

    public CopyOnWriteArrayList<CommunicationThread> getCommunicationThreads() {
        return CommunicationThreads;
    }

    public CommunicationThread getCommunicationThread(Character label) {
        for (CommunicationThread communicationThread : CommunicationThreads) {
            if(communicationThread.getNodeConnectedTo() == label)
                return communicationThread;
        }

//        int mark = -1;
//        for (int i = this.CommunicationThreads.size() - 1; i >= 0; i--) {
//            if(this.CommunicationThreads.get(i).getNodeConnectedTo() == label) {
//                mark = i;
//                break;
//            }
//        }
        return null;
    }

    public void shutDown() {
        this.shutDown = this.disconnectAllConnections();
    }

    private boolean disconnectAllConnections() {
        ArrayList<Character> nodesToRemove = new ArrayList<>();
        for (CommunicationThread communication : this.CommunicationThreads) {
            nodesToRemove.add(communication.getNodeConnectedTo());
        }
        return disconnectNode(nodesToRemove);
    }

    public String printCommunicationThreads() {
        StringBuilder info = new StringBuilder("");
        for (CommunicationThread communicationThread : CommunicationThreads) {
            info.append(communicationThread.getNodeConnectedTo())
                    .append(" at ").append(communicationThread.getHostName())
                    .append(":").append(communicationThread.getPort()).append("\n");
        }
        return info.toString();
    }

    public boolean disconnectNode(String[] nodesList) {
        if (nodesList.length == 1) {
            System.err.println("Need node label to delete...");
            return false;
        }
        ArrayList<Character> nodesToRemove = new ArrayList<>();
        synchronized (this.CommunicationThreads) {
            for (CommunicationThread communication : this.CommunicationThreads) {
                if (nodesList[1].indexOf(communication.getNodeConnectedTo()) != -1) {
                    System.out.println("Disonnecting from " + communication.getNodeConnectedTo());
                    nodesToRemove.add(communication.getNodeConnectedTo());
                }
            }
        }
        return disconnectNode(nodesToRemove);
    }
    private synchronized boolean disconnectNode(ArrayList<Character> nodesToRemove) {
        boolean ack = true;
        for (Character label : nodesToRemove) {
            CommunicationThread communicationThread = getCommunicationThread(label);
            if(communicationThread != null) {
                communicationThread.close();
                this.CommunicationThreads.remove(communicationThread);
            }
            else
                ack = false;
        }
        return ack;
    }

    public void connectNode(String[] nodesList) {
        ArrayList<Character> nodesToAdd = new ArrayList<>();
        if(Files.notExists(Paths.get(NETWORK_CONFIG)))
            return ;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(NETWORK_CONFIG));
            if(lines.size() == 0)
                return ;
            for (String line : lines) {
                String[] split = line.split("\t");
//                System.out.println(split[0]+ Integer.parseInt(split[1])+ split[2].charAt(0));
                if (nodesList[1].indexOf(split[2].charAt(0)) != -1)
                    (new ConnectionInitiator(split[0], Integer.parseInt(split[1]), split[2].charAt(0))).connect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialConnection() {
        if(Files.notExists(Paths.get(NETWORK_CONFIG)))
            return;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(NETWORK_CONFIG));
            if(lines.size() == 0)
                return;
            for (String line : lines) {
                String[] split = line.split("\t");
//                System.out.println(split[0]+ Integer.parseInt(split[1])+ split[2].charAt(0));
                (new ConnectionInitiator(split[0], Integer.parseInt(split[1]), split[2].charAt(0))).connect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEntryToFile() {
        try {
            StringBuilder content = new StringBuilder(InetAddress.getLocalHost().getHostName());
            content.append("\t").append(this.port).append("\t").append(this.ID);

            List<String> lines = new ArrayList<String>();
            lines.add(content.toString());
            Files.write(Paths.get(NETWORK_CONFIG), lines, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (UnknownHostException e) {
            System.err.println("\nUnknownHostException when get local hostname: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("\naError when add local host to configuration file: " + e.getMessage());
        }
    }
    private void removeEntryFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(NETWORK_CONFIG));
            List<String> new_lines = new ArrayList<>();
            for (String line : lines) {
                if(!line.contains("\t" + this.ID))
                    new_lines.add(line);
            }
            Files.write(Paths.get(NETWORK_CONFIG), new_lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
