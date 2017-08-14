import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanlin on 2/17/17.
 */
public class Node {
    private int id;
    private boolean odd_even;
    private int size;
    private int criticalSectionCounter;
    private ServerSocket serverSocket;

    private long currentRequestTimestamp;

    private long currentRequestRealTime;

    private LogicalClock logicalClock;

    private int pendingAckNum;

    private boolean[] pendingAckList;

    private boolean[] withholdAckList;

    private boolean executingCriticalZone;

    private boolean [] messageList;


    private int phase;

    private int requestMessageSent;
    private int ackMessageSent;


    private int curRequestMessageSent;

    public Node(int id, int size) {
        this.id = id;
        this.odd_even = (id / 2) * 2 != id ? true : false;
        this.size = size;

        this.criticalSectionCounter = 0;

        try {
            this.serverSocket = new ServerSocket(this.id + 63780);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.currentRequestTimestamp = -1;
        this.currentRequestRealTime = 0;

        this.logicalClock = LogicalClock.getInstance();

        this.pendingAckNum = id - 1;

        this.pendingAckList = new boolean[this.size + 1];
        for (int i = 1; i <= this.size; i++) {
            this.pendingAckList[i] = this.id > i ? true : false;
        }

        for (boolean b : this.withholdAckList = new boolean[size + 1]) {
            b = false;
        }

        this.executingCriticalZone = false;

        for (boolean b : this.messageList = new boolean[size + 1]) {
            b = false;
        }

        this.phase = 1;

        this.requestMessageSent = 0;
        this.ackMessageSent = 0;
        this.curRequestMessageSent = 0;
    }

    public synchronized boolean checkWithholdAck() {
        return withholdAckList[0];
    }

    public synchronized boolean checkWithholdAck(int id) {
        return withholdAckList[id];
    }

    public synchronized void withholdAck(int id) {
        withholdAckList[id] = true;
    }

    public synchronized void deliverAck(int id) {
        this.withholdAckList[id] = false;
        this.pendingAckList[id] = true;
        boolean allSent = false;
        for (int i = 1; i <= this.size; i++) {
            allSent = allSent || this.withholdAckList[i];
        }
        if (allSent == false) {
            this.withholdAckList[0] = false;
        }
    }

    public synchronized void resetPendingAckNum() {
        this.pendingAckNum = 0;
        for (int i = 1; i <= this.size; i++)
            if (this.pendingAckList[i]) {
                this.pendingAckNum++;
                this.messageList[i] = true;
        }
    }

    public int getSize() {
        return size;
    }

    public boolean[] getPendingAckList() {
        return pendingAckList;
    }

    public synchronized void receiveAck(int id) {
        if (!this.pendingAckList[id])
            System.err.println("Node " + this.id + " do not need ack from node " + id);
        else {
            this.pendingAckList[id] = false;
            this.pendingAckNum--;
        }
    }

    public synchronized void setMessageList() {
        for (int i = 1; i <= this.size; i++) {
            this.messageList[i] = this.pendingAckList[i] ? true : false;
//            System.out.println("this.messageList[" + i + "] = " + this.messageList[i]);
        }
    }

    public synchronized void readyToSendAck() {
        this.withholdAckList[0] = true;
    }


    public synchronized void deliverMessageList(int id) {
        this.messageList[id] = false;
    }

    public synchronized boolean getMessageList(int id) {
        return messageList[id];
    }

    public int getId() {
        return id;
    }

    public LogicalClock getLogicalClock() {
        return logicalClock;
    }

    public boolean isExecutingCriticalZone() {
        return executingCriticalZone;
    }

    public long getCurrentRequestTimestamp() {
        return currentRequestTimestamp;
    }

    public void setCurrentRequestTimestamp() {
        this.currentRequestTimestamp = this.logicalClock.getLogicalTime();
    }

    public boolean checkWhetherReplyAck(long otherTimestamp, int otherID) {
        if (this.executingCriticalZone == false &&
                (this.currentRequestTimestamp == -1 ||
                this.currentRequestTimestamp > otherTimestamp ||
                (this.currentRequestTimestamp == otherTimestamp && this.id > otherID)))
            return true;
        else
            return false;
    }

    public int getPhase() {
        return phase;
    }

    public boolean isOdd_even() {
        return odd_even;
    }

    public synchronized void addRequestMessageSent() {
        this.requestMessageSent++;
    }

    public synchronized void addAckMessageSent() {
        this.ackMessageSent++;
    }

    public synchronized void addCurRequestMessageSent() {
        this.curRequestMessageSent++;
    }

    public synchronized void resetCurRequestMessageSent() {
        this.curRequestMessageSent = 0;
    }

    public int getCriticalSectionCounter() {
        return criticalSectionCounter;
    }

    public void init(){
        /*
        For each node with greater id, wait for their socket connections.
        Create a new thread to deal with the socket on accept.
        */
        for(int i = this.size; i > this.id; i--) {
            try {
                Socket socket = this.serverSocket.accept();

                System.out.println("New connections coming in.");

                new Thread(new CommunicateThread(i, socket, this)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        For each node with smaller id, try to start the socket connections.
        Create a new thread to deal with the socket on trying.
        */
        for(int i = 1; i < this.id; i++) {
            try {
                System.out.println("Trying connecting to dc0"+i+".utdallas.edu ...");
                Socket socket = new Socket("dc0"+i+".utdallas.edu", i + 63780);

                System.out.println("New connections established.");

                new Thread(new CommunicateThread(i, socket, this)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        System.out.println("Starting..");
        new Thread(new RequestGenerator(this)).start();
        while(criticalSectionCounter < 40) {
            synchronized (this) {
                while (this.currentRequestTimestamp == -1) {
                    try {
                        wait(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (this.pendingAckNum != 0) {
                    try {
                        wait(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (pendingAckNum != 0) {
                System.err.println("[Error]: pendingAckNum != 0");
            }

            try {
                LogicalClock.getInstance().logicalTimeIncrease((long)this.id);
                this.executeCriticalZone();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.readyToSendAck();

            if (criticalSectionCounter > 20)
                phase = 2;
        }
        phase = 3;

        System.out.println("\nTotal request messages sent: " + this.requestMessageSent);
        System.out.println("Total ack messages sent: " + this.ackMessageSent);
    }

    private void executeCriticalZone() throws IOException {
        this.executingCriticalZone = true;

        long curTime = new Date().getTime();

        recordTime(curTime - this.currentRequestRealTime);
        resetCurRequestMessageSent();

        Date date = new Date();

        String home = System.getProperty("user.home");

        File f = new File(System.getProperty("user.home") + "/record.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        Files.write(Paths.get(home + "/record.txt"), ("entering node " + this.getId() + " \t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date.getTime()) + "\n").getBytes(), StandardOpenOption.APPEND);

        synchronized (this) {
            try {
                wait(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.criticalSectionCounter++;
        System.out.println("Critical Section Counter: " + this.criticalSectionCounter);

        this.currentRequestTimestamp = -1;

        this.getLogicalClock().logicalTimeIncrease(id);

        this.executingCriticalZone = false;
    }

    public void setCurrentRequestRealTimestamp() {
        this.currentRequestRealTime = new Date().getTime();
    }

    public void recordTime(long waitingTime) throws IOException {
        Date date = new Date();

        String home = System.getProperty("user.home");

        File f = new File(System.getProperty("user.home") + "/data" + this.id + ".txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        String time = "Node " + id + "\tentering the " + (criticalSectionCounter + 1) + "\tcritical zone at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date.getTime()) + "\twaiting time: " + waitingTime;
        String number = "\tRequest messages number: " + this.curRequestMessageSent + "\n";
        Files.write(Paths.get(home + "/data" + this.id + ".txt"), (time + number).getBytes(), StandardOpenOption.APPEND);

    }
}
