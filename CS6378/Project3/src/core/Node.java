package core;

import net.CommunicationThread;
import net.ConnectionInitiator;
import net.ListenerThread;

import log.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

public class Node {
    private static Node ourInstance = new Node();

    public static Node getInstance() {
        return ourInstance;
    }

    private static final String NETWORK_CONFIG = "NetworkConfig.txt";

    private boolean shutDown;
    private boolean lock;
    private char ID;
    private SynchronousQueue<Message> messageQueue;
    private CopyOnWriteArrayList<CommunicationThread> CommunicationThreads;
    private VoteData localVoteData;
    private VoteData latestVoteData;
    private ArrayList<Character> latestVoteSite;
    private ArrayList<Character> allVoteSite;
    private int totalVoteReceive;
    private int port;

    private Node() {
        this.shutDown = false;
        this.lock = false;
        this.messageQueue = new SynchronousQueue<>();
        this.CommunicationThreads = new CopyOnWriteArrayList<>();
        this.localVoteData = null;
        this.latestVoteData = null;
        this.allVoteSite = null;
        this.totalVoteReceive = 0;
        this.port = new Random().nextInt(65535);
    }

    public void setID(char ID) {
        this.ID = ID;
    }

    public void start() {
        new Thread(new ListenerThread(this.port)).start();
        try {
            this.initiateConnection();
            this.addEntryToFile();

            new Thread(new MessageProcessorThread()).start();
            Thread.sleep(500);
            new Thread(new CLIEngine()).start();
            while (!this.shutDown)
                Thread.sleep(500);
            this.removeEntryFromFile();
        } catch (InterruptedException e) {
            System.err.println("\nProcess interupted: " + e.getMessage());
        }
        System.exit(0);
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

    private CommunicationThread getCommunicationThread(Character label) {
        for (CommunicationThread communicationThread : CommunicationThreads) {
            if (communicationThread.getNodeConnectedTo() == label)
                return communicationThread;
        }
        return null;
    }

    void shutDown() {
        this.disconnectAllConnections();
        this.shutDown = true;
    }

    private void disconnectAllConnections() {
        ArrayList<Character> nodesToRemove = new ArrayList<>();
        for (CommunicationThread communication : this.CommunicationThreads) {
            nodesToRemove.add(communication.getNodeConnectedTo());
        }
        this.disconnectNode(nodesToRemove);
    }

    String printCommunicationThreads() {
        StringBuilder info = new StringBuilder("\nCurrent connections are:\n");
        for (CommunicationThread communicationThread : this.CommunicationThreads) {
            info.append("    ")
                    .append(communicationThread.getNodeConnectedTo())
                    .append(" at ").append(communicationThread.getHostName())
                    .append(":").append(communicationThread.getPort()).append("\n");
        }
        if (this.CommunicationThreads.size() == 0)
            info.append("    none.");
        return info.toString();
    }

    void disconnectNode(String[] nodesList) {
        if (nodesList.length == 1) {
            System.err.println("Need node label to delete...");
            return;
        }
        ArrayList<Character> nodesToRemove = new ArrayList<>();
        for (int i = 1; i < nodesList.length; i++) {
            for (CommunicationThread communication : this.CommunicationThreads) {
                if (nodesList[i].equalsIgnoreCase("" + communication.getNodeConnectedTo())) {
                    System.out.println("Disconnecting from " + communication.getNodeConnectedTo());
                    nodesToRemove.add(communication.getNodeConnectedTo());
                }
            }
        }
        disconnectNode(nodesToRemove);
    }

    private synchronized void disconnectNode(ArrayList<Character> nodesToRemove) {
        for (Character label : nodesToRemove) {
            CommunicationThread communicationThread = getCommunicationThread(label);
            if (communicationThread != null) {
                communicationThread.close();
                this.CommunicationThreads.remove(communicationThread);
            }
        }
    }

    void connectNode(String[] nodesList) {
        if (Files.notExists(Paths.get(NETWORK_CONFIG)))
            return;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(NETWORK_CONFIG));
            if (lines.size() == 0)
                return;
            for (String line : lines) {
                String[] split = line.split("\t");
                for (int i = 1; i < nodesList.length; i++) {
                    if (nodesList[i].equalsIgnoreCase("" + split[2].charAt(0)))
                        (new ConnectionInitiator(split[0], Integer.parseInt(split[1]), split[2].charAt(0))).connect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initiateConnection() {
        if (Files.notExists(Paths.get(NETWORK_CONFIG)))
            return;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(NETWORK_CONFIG));
            if (lines.size() == 0)
                return;
            for (String line : lines) {
                String[] split = line.split("\t");
                (new ConnectionInitiator(split[0], Integer.parseInt(split[1]), split[2].charAt(0))).connect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEntryToFile() {
        try {
            List<String> lines = new ArrayList<>();
            lines.add(InetAddress.getLocalHost().getHostName() + "\t" + this.port + "\t" + this.ID);
            Files.write(Paths.get(NETWORK_CONFIG), lines, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (UnknownHostException e) {
            System.err.println("\nUnknownHostException when get local hostname: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("\nError when add local host to configuration file: " + e.getMessage());
        }
    }

    private void removeEntryFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(NETWORK_CONFIG));
            List<String> new_lines = new ArrayList<>();
            for (String line : lines) {
                if (!line.contains("\t" + this.ID))
                    new_lines.add(line);
            }
            Files.write(Paths.get(NETWORK_CONFIG), new_lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initiateVoteDataInitialization() {
        this.voteDataInitialize();
        this.broadcast(MessageType.INIT_VOTE);
    }

    void voteDataInitialize() {
        this.localVoteData = new VoteData(1, this.CommunicationThreads.size() + 1, this.selectDistinguishSite());
    }

    private ArrayList<Character> selectDistinguishSite() {
        ArrayList<Character> ds = null;

        if (this.CommunicationThreads.size() == 0) {
            System.err.println("Error in selecting distringuishi site: " + this.CommunicationThreads.size() + " exist.");
        }

        /* When SC = 3, DS lists those three sites from which a majority is needed to form a distinguished partition. */
        if (this.CommunicationThreads.size() == 2) {
            ds = new ArrayList<>(3);
            ds.add(this.ID);
            for (CommunicationThread communicationThread : this.CommunicationThreads) {
                ds.add(communicationThread.getNodeConnectedTo());
            }
        }

        /* Odd number of communication threads means even number of nodes in network. */
        if (this.CommunicationThreads.size() % 2 != 0) {
            Character dslabel = this.ID;
            for (CommunicationThread communicationThread : this.CommunicationThreads) {
                if (communicationThread.getNodeConnectedTo() < dslabel) {
                    dslabel = communicationThread.getNodeConnectedTo();
                }
            }
            ds = new ArrayList<>(1);
            ds.add(dslabel);
        }

        /* If none of the above condition satisfied, i.e. number of communication threads is even and not equals to 0.
         * The number of nodes  in network is odd and not equals to 3, no need to select a distinguish site.
         * ds remain null. */

        return ds;
    }

    VoteData getLocalVoteData() {
        return localVoteData;
    }

    private void broadcast(MessageType messageType) {
        Message message = new Message(messageType, LocalDateTime.now(), this.ID);
        switch (messageType) {
            case INIT_VOTE:
            case VOTE_REQ:
            case ABORT:
                for (CommunicationThread communicationThread : this.CommunicationThreads) {
                    communicationThread.send(message);
                }
                break;
            case COMMIT:
                message.setContent(this.localVoteData);
                for (CommunicationThread communicationThread : this.CommunicationThreads) {
                    communicationThread.send(message);
                }
                break;
            default:
                System.err.println("Message type " + messageType + " shouldn't use broadcast.");
        }
    }

    private void send(Character ID, MessageType messageType) {
        Message message = new Message(messageType, LocalDateTime.now(), this.ID);
        switch (messageType) {
            case VOTE_REQ_ACK:
                message.setContent(new VoteData(this.localVoteData.getVN(), this.localVoteData.getSC(), this.localVoteData.getDS()));
                break;
            case VOTE_REQ_NACK:
                break;
        }
        for (CommunicationThread communicationThread : this.CommunicationThreads) {
            if (communicationThread.getNodeConnectedTo() == ID) {
                communicationThread.send(message);
            }
        }
    }

    synchronized void write() {
        if (this.acquireLock()) {

            this.requestVote();
            while (this.allVoteSite.size() <= this.CommunicationThreads.size()) {
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    System.err.println("\nInterupted in collecting vote: " + e.getMessage());
                }
            }

            /* After all ACK/NACK received, if total vote number < reply node number,
            * i.e. some node reply NACK, abort write operation. */
            if (this.totalVoteReceive < this.allVoteSite.size() - 1) {
                this.abort();
                this.resetVoteData();
                this.releaseLock();
                new Thread(new log(this.ID,EventType.WRITE_FAIL_WITH_NACK,
                        LocalDateTime.now(), this.localVoteData.toStringCompact())).start();
                return;
            }

            /* If S does not belong to a distinguished partition, it aborts the update,
            * issues a RELEASE-LOCK request to its local lock manager,
            * and sends ABORT messages to all the participants. */
            if (!this.isDistinguished()) {
                System.out.println("UPDATE FAIL:\n\tCurrent node does not belong to a distinguished partition. ");
                this.abort();
                this.resetVoteData();
                this.releaseLock();
                new Thread(new log(this.ID,EventType.WRITE_FAIL_NO_DISTINGUISH,
                        LocalDateTime.now(), this.localVoteData.toStringCompact())).start();
                return;
            }

            /* If S does belong to a distinguished partition, it determines if its copy is current;
            * if it is not current, S determines what subordinates have current copies of the file f. */
            if (this.localVoteData.getVN() != this.latestVoteData.getVN())
                this.catchup();

            System.out.println("UPDATE SUCCESS!");
            this.commit();
            this.broadcast(MessageType.COMMIT);
            this.resetVoteData();
            new Thread(new log(this.ID,EventType.WRITE_SUCCESS,
                    LocalDateTime.now(), this.localVoteData.toStringCompact())).start();
            this.releaseLock();
        }
    }

    private synchronized boolean acquireLock() {
        if (!this.lock) {
            this.lock = true;
            return true;
        } else
            return false;

    }

    private synchronized void releaseLock() {
        if (this.lock) {
            this.lock = false;
        }
    }

    synchronized void addVote(Character label, VoteData voteData) {
        this.allVoteSite.add(label);

        if (voteData == null) {
            System.err.println("No vote data is found.");
            return;
        }

        this.totalVoteReceive++;

        if (this.latestVoteData.getVN() < voteData.getVN()) {
            this.latestVoteData = voteData;
            this.latestVoteSite = new ArrayList<>();
            this.latestVoteSite.add(label);
        } else if (this.latestVoteData.getVN() == voteData.getVN()) {
            this.latestVoteSite.add(label);
        }
    }

    private void requestVote() {
        if (this.latestVoteSite != null || this.latestVoteData != null) {
            System.err.println("Cleanup for last request should be done before new request coming in.");
        }
        this.latestVoteSite = new ArrayList<>();
        this.latestVoteSite.add(this.ID);
        this.allVoteSite = new ArrayList<>();
        this.allVoteSite.add(this.ID);
        this.latestVoteData = new VoteData(this.localVoteData.getVN(), this.localVoteData.getSC(), this.localVoteData.getDS());

        this.broadcast(MessageType.VOTE_REQ);
    }

    void replyVote(Character senderID) {
        if (this.acquireLock()) {
            this.send(senderID, MessageType.VOTE_REQ_ACK);
        } else {

            this.send(senderID, MessageType.VOTE_REQ_NACK);
        }
    }

    /* Is-Distinguished routine in paper. */
    private boolean isDistinguished() {
        /* If card(I) > N/2, then S is a member of a distinguished partition. */
        if (this.latestVoteSite.size() > this.latestVoteData.getSC() / 2) {
            return true;
        }

        /* Otherwise, if card(I) = N/2, then select any site S, in I. (Any site in I will do; the choice is arbitrary.)
        * If DS belongs to I, then S belongs to a distinguished partition. */
        if (this.latestVoteSite.size() == this.latestVoteData.getSC() / 2) {
            if (this.contain(this.latestVoteData.getDS(), this.latestVoteSite)) {
                return true;
            }
            /* Otherwise, if N = 3, then the site S examines the value of the distinguished sites list DS, of the site in I.
            * (Since first if-statement did not apply, there must be only a single site in I.)
            * If P contains two or all three of the sites listed in DS, then also does S lie in a distinguished partition.
            * (Note that we do not require that these sites be in I, but only that they be in P.) */
            if (this.allVoteSite.size() == 3) {
                if (this.conjunction2(this.latestVoteData.getDS(), this.allVoteSite))
                    return true;
            }
        }
        return false;
    }

    void cancel() {
        this.releaseLock();
        new Thread(new log(this.ID,EventType.WRITE_ABORT,
                LocalDateTime.now(), this.localVoteData.toStringCompact())).start();
    }

    private void abort() {
        this.broadcast(MessageType.ABORT);
    }

    /* Return true if the second ArrayList 'list' contains all elements in first ArrayList 'element',
    * otherwise return false. */
    private boolean contain(ArrayList<Character> elements, ArrayList<Character> list) {
        if (elements == null)
            return false;

        boolean result = true;

        for (Character c : elements) {
            result = result && list.contains(c);
        }
        return result;
    }

    /* Return true if two arraylists' conjunction is greater than 2, otherwise return false. */
    private boolean conjunction2(ArrayList<Character> a, ArrayList<Character> b) {
        int count = 0;
        boolean result = true;
        for (Character c : a) {
            if (b.contains(c))
                count++;
        }
        return count >= 2;
    }

    private void catchup() {
        /* To-do: Currently no data has been invoke, so no catchup is needed. */
    }

    void commit(VoteData voteData) {
        this.localVoteData = voteData;
        this.releaseLock();
        new Thread(new log(this.ID,EventType.WRITE_REQUEST_SUCCESS,
                LocalDateTime.now(), this.localVoteData.toStringCompact())).start();
    }

    private void commit() {
        /* if card(P) is even, and S' > S'' for all other S'' in P */
        ArrayList<Character> newds;
        int newsc = this.allVoteSite.size();

        /* if N = 3 and card ( P) = 2, then there is no change made to SC and DS */
        if (this.latestVoteData.getSC() == 3 && this.allVoteSite.size() == 2) {
            newds = this.localVoteData.getDS();
            newsc = 3;
        } else if (this.allVoteSite.size() % 2 == 0) {
            Character s = this.ID;
            for (Character c : this.allVoteSite) {
                if (s > c)
                    s = c;
            }
            newds = new ArrayList<>(1);
            newds.add(s);
        } else if (this.allVoteSite.size() == 3) {
            newds = this.allVoteSite;
        } else {
            newds = null;
        }
        this.localVoteData = new VoteData(this.latestVoteData.getVN() + 1, newsc, newds);
    }

    private void resetVoteData() {
        this.totalVoteReceive = 0;
        this.allVoteSite = null;
        this.latestVoteData = null;
        this.latestVoteSite = null;
    }

    @Override
    public String toString() {
        return "Current Node Status" +
                "\nshutDown =\t " + shutDown +
                "\nlock =\t " + lock +
                "\nID =  \t" + ID +
                "\nmessageQueue=" + messageQueue +
                "\nCommunicationThreads=" + CommunicationThreads +
                "\nlocalVoteData=" + localVoteData +
                "\nlatestVoteData=" + latestVoteData +
                "\nlatestVoteSite=" + latestVoteSite +
                "\nallVoteSite=" + allVoteSite +
                "\ntotalVoteReceive=" + totalVoteReceive +
                "\nport=" + port;
    }
}
