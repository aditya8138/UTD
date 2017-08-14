import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

    private Integer id;
    private VectorTime vectorTime;
    private ServerStatus serverStatus;
    private HostConfiguration hostConfiguration;
    private ConcurrentSkipListMap<Integer, StoredContent> contentStorage;
    private ConcurrentSkipListMap<Integer, TreeMap<VectorTime, StoredContent>> buffer;
    private TreeMap<Integer, Socket> socketTreeMap;

    Server(HostConfiguration hostConfiguration) {
        this.id = hostConfiguration.getId();
        this.vectorTime = new VectorTime(hostConfiguration.getServerNum());
        this.contentStorage = new ConcurrentSkipListMap<>();
        this.buffer = new ConcurrentSkipListMap<>();
        this.serverStatus = ServerStatus.normal;
        this.socketTreeMap = new TreeMap<>();
        this.hostConfiguration = hostConfiguration;

    }

    private synchronized void ticktock() {
        this.vectorTime.ticktock(this.id);
    }

    private synchronized void ticktock(VectorTime vectorTime, Integer FromID) {
        this.vectorTime.ticktock(this.id, vectorTime, FromID);
    }

    private ServerStatus getServerStatus() {
        return serverStatus;
    }

    private ConcurrentSkipListMap<Integer, StoredContent> getContentStorage() {
        return contentStorage;
    }

    void start() throws IOException {
        new Thread(new ServerListenerThread(this.hostConfiguration.getPort())).start();
        new Thread(new InteractiveServerThread()).start();
    }

    private class ServerListenerThread implements Runnable {
        private ServerSocket serverSocket;

        private ServerListenerThread(Integer port) throws IOException {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Start Listening...");
        }


        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(new ServerMessageHandlerThread(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class ServerMessageHandlerThread implements Runnable {
        private Socket socket;

        private ServerMessageHandlerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectInputStream oin;
            ObjectOutputStream oout;

            try {
                oout = new ObjectOutputStream(socket.getOutputStream());
                oin = new ObjectInputStream(socket.getInputStream());

                boolean terminate = false;
                while (!terminate) {
                    try {
                        Message receivedMsg = (Message) oin.readObject();
                        if (getServerStatus() != ServerStatus.fail)
                            ticktock(receivedMsg.getTimestamp(), receivedMsg.getFrom());
                        if ((receivedMsg != null ? receivedMsg.getMessageType() : null) == MessageType.server_put_end)
                            terminate = true;
                        else {
                            Message replyMsg = generateReplyMsg(receivedMsg);
                            ticktock();
                            oout.writeObject(replyMsg);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        terminate = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private synchronized Message generateReplyMsg(Message receivedMsg) {
        if (serverStatus == ServerStatus.fail)
            return new Message(MessageType.server_unavailable, null, id, vectorTime);
        switch (receivedMsg.getMessageType()) {
            case client_get: return generateReplyMsgClientGet(receivedMsg);
            case client_put: return generateReplyMsgClientPut(receivedMsg);
            case server_put_req: return generateReplyMsgServerPutReq(receivedMsg);
            case server_put: return generateReplyMsgServerPut(receivedMsg);
            default: System.out.println("Undefined message type."); return null;
        }
    }

    private Message generateReplyMsgClientGet(Message receivedMsg) {
        if (receivedMsg.getStoredContent() != null) {
            return new Message(MessageType.client_get_ack,
                    getContentStorage().get(receivedMsg.getStoredContent().getContentID()), id, vectorTime);
        } else
            return null;
    }

    private Message generateReplyMsgClientPut(Message receivedMsg) {
        boolean primaryACK, secodaryACK, tertiaryACK;
        primaryACK = secodaryACK = tertiaryACK = false;

        if (receivedMsg.getStoredContent() != null) {
            Integer primaryHash = HostInfo.getInstance().HashPrimary(receivedMsg.getStoredContent().getContentID());
            Integer secondaryHash = HostInfo.getInstance().HashSecondary(receivedMsg.getStoredContent().getContentID());
            Integer tertiaryHash = HostInfo.getInstance().HashTertiary(receivedMsg.getStoredContent().getContentID());

            if (primaryHash.equals(id) || secondaryHash.equals(id) || tertiaryHash.equals(id)) {

                /* The StoredContent instance was created by a client, so there is no timestamp in the instance.*/
                /* Need to set the timestamp to impose the sequence. */
                receivedMsg.getStoredContent().setVectorTime(this.vectorTime.getTimestamp());
                buffContent(receivedMsg.getStoredContent());

                Message serverPutReqMsg = new Message(MessageType.server_put_req, receivedMsg.getStoredContent(), id);
                if (!Objects.equals(primaryHash, id))
                    primaryACK = writeReqToOtherServer(serverPutReqMsg, primaryHash);
                if (!Objects.equals(secondaryHash, id))
                    secodaryACK = writeReqToOtherServer(serverPutReqMsg, secondaryHash);
                if (!Objects.equals(tertiaryHash, id))
                    tertiaryACK = writeReqToOtherServer(serverPutReqMsg, tertiaryHash);

                if (primaryACK || secodaryACK || tertiaryACK) {
                    Message serverPutMsg = new Message(MessageType.server_put, receivedMsg.getStoredContent(), id);
                    if (!Objects.equals(primaryHash, id))
                        primaryACK = writeToOtherServer(serverPutMsg, primaryHash);
                    if (!Objects.equals(secondaryHash, id))
                        secodaryACK = writeToOtherServer(serverPutMsg, secondaryHash);
                    if (!Objects.equals(tertiaryHash, id))
                        tertiaryACK = writeToOtherServer(serverPutMsg, tertiaryHash);
                    write(receivedMsg);
                }
            } else
                System.out.print("Wrong server to store.");
        }
        if (primaryACK || secodaryACK || tertiaryACK)
            return new Message(MessageType.client_put_ack, receivedMsg.getStoredContent(), id);
        else
            return new Message(MessageType.client_put_fail, receivedMsg.getStoredContent(), id);
    }

    private Message generateReplyMsgServerPutReq(Message receivedMsg) {
        if (receivedMsg.getStoredContent() == null)
            return new Message(MessageType.server_put_fail, null, id, vectorTime);
        this.buffContent(receivedMsg.getStoredContent());
        return new Message(MessageType.server_put_req_ack, null, id, vectorTime);
    }

    private Message generateReplyMsgServerPut(Message receivedMsg) {
        this.write(receivedMsg);
        return new Message(MessageType.server_put_ack, null, id, vectorTime);
    }

    private boolean writeReqToOtherServer(Message message, Integer id) {
        ticktock();
        boolean result = false;

        Socket socket;
        ObjectInputStream oin;
        ObjectOutputStream oout;
        try {
            socket = new Socket(HostInfo.getInstance().getServerNodes().get(id).getHostname(),
                    HostInfo.getInstance().getServerNodes().get(id).getPort());
            oin = new ObjectInputStream(socket.getInputStream());
            oout = new ObjectOutputStream(socket.getOutputStream());

            oout.writeObject(message);

            Message replyMsg = (Message) oin.readObject();
            if (replyMsg.getMessageType() != MessageType.server_unavailable)
                ticktock(replyMsg.getTimestamp(), replyMsg.getFrom());
            if (replyMsg.getMessageType() == MessageType.server_put_req_ack)
                result = true;

            ticktock();
            oout.writeObject(new Message(MessageType.server_put_end, null, id, vectorTime));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean writeToOtherServer(Message message, Integer id) {
        ticktock();
        boolean result = false;

        Socket socket;
        ObjectInputStream oin;
        ObjectOutputStream oout;
        try {
            socket = new Socket(HostInfo.getInstance().getServerNodes().get(id).getHostname(),
                    HostInfo.getInstance().getServerNodes().get(id).getPort());
            oin = new ObjectInputStream(socket.getInputStream());
            oout = new ObjectOutputStream(socket.getOutputStream());

            oout.writeObject(message);

            Message replyMsg = (Message) oin.readObject();
            if (replyMsg.getMessageType() != MessageType.server_unavailable)
                ticktock(replyMsg.getTimestamp(), replyMsg.getFrom());
            if (replyMsg.getMessageType() == MessageType.server_put_ack)
                result = true;

            ticktock();
            oout.writeObject(new Message(MessageType.server_put_end, null, id, vectorTime));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    private synchronized void buffContent(StoredContent storedContent) {
        TreeMap<VectorTime, StoredContent> buffer = this.buffer.get(storedContent.getContentID());

        if (buffer == null) {
            buffer = new TreeMap<>();
            this.buffer.put(storedContent.getContentID(), buffer);
        }
        buffer.put(storedContent.getVectorTime(), storedContent);
    }

    private synchronized void write(Message receivedMsg) {
        if (buffer.get(receivedMsg.getStoredContent().getContentID()) == null)
            return;
        for (Map.Entry<VectorTime, StoredContent> bufferEntry : buffer.get(receivedMsg.getStoredContent().getContentID()).entrySet()) {
            if (bufferEntry.getValue().getVectorTime().compareTo(receivedMsg.getStoredContent().getVectorTime()) <= 0) {
                record(receivedMsg.getFrom(), receivedMsg.getStoredContent().getContentID(),
                            contentStorage.get(receivedMsg.getStoredContent().getContentID()) != null ?
                                    contentStorage.get(receivedMsg.getStoredContent().getContentID()).getIntegerValue() : null,
                        receivedMsg.getStoredContent().getIntegerValue());
                contentStorage.put(receivedMsg.getStoredContent().getContentID(), bufferEntry.getValue());
                buffer.get(receivedMsg.getStoredContent().getContentID()).remove(bufferEntry.getKey());
            }
        }
        if (buffer.get(receivedMsg.getStoredContent().getContentID()).size() == 0)
            buffer.remove(receivedMsg.getStoredContent().getContentID());
    }

    private void record(Integer clientId, Integer fid, Integer oldValue, Integer newValue){
        Date date = new Date();

        String home = System.getProperty("user.home");

        File f = new File(System.getProperty("user.home") + "/data" + this.id + ".txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuilder record = new StringBuilder("Content ");
        record.append(fid).append(" was modified from ").append(oldValue != null ? oldValue : "null")
                .append("   \tto ").append(newValue).append("   \tby node ").append(clientId).append("\tat ")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date.getTime())).append("\n");
        try {
            Files.write(Paths.get(home + "/data" + this.id + ".txt"), (record.toString()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class InteractiveServerThread implements Runnable {

        @Override
        public void run() {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            boolean terminate = false;

            while (!terminate) {
                try {
                    line = bufferedReader.readLine();
                    if (line.equals(""))
                        continue;
                    String[] cmd = line.split(" ");
                    if (cmd[0].equals("offline"))
                        serverStatus = ServerStatus.fail;
                    if (cmd[0].equals("online"))
                        serverStatus = ServerStatus.normal;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", vectorTime=" + vectorTime +
                ", serverStatus=" + serverStatus +
                ", hostConfiguration=" + hostConfiguration +
                ", contentStorage=" + contentStorage +
                ", buffer=" + buffer +
                ", socketTreeMap=" + socketTreeMap +
                '}';
    }
}
