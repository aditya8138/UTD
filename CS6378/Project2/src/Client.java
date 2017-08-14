import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Integer.valueOf;

/**
 * Created by hanlin on 3/29/17.
 */
public class Client {
    Integer id;
    private HashMap<Integer, Socket> connections;
    private HashMap<Integer, ObjectInputStream> oins;
    private HashMap<Integer, ObjectOutputStream> oouts;

    public Client(Integer id) {
        this.id = id;
        this.connections = new HashMap<>();
        this.oins = new HashMap<>();
        this.oouts = new HashMap<>();
    }

    void start() throws IOException, ClassNotFoundException {
        System.out.println("Client start.");
        this.establishConnection();

        String cliOperationType, cliServerIdx;
        Integer cliContentID, cliContentValue, serverID;
        Long beginTime, endTime;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (true) {
            line = bufferedReader.readLine();
            if (line.equals(""))
                continue;
            if (line.equals("terminate"))
                break;
            String[] cmd = line.split(" ");
            cliServerIdx = cmd[0].trim();
            cliOperationType = cmd[1].trim();
            cliContentID = valueOf(cmd[2].trim());
            cliContentValue = (cmd.length == 4) ? valueOf(cmd[3].trim()) : null;

            if (!operation(cliServerIdx, cliOperationType, cliContentID, cliContentValue))
                break;
        }
    }

    synchronized void autostart() throws IOException, ClassNotFoundException {
        System.out.println("Automated Client start.");
        this.establishConnection();

        String cliOperationType, cliServerIdx;
        Integer cliContentID, cliContentValue, serverID;
        Long beginTime, endTime;

        Random r = new Random();
        String abc = "abc";

        for (int i = 1; i <= 30; i++) {
            try {
                wait(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cliServerIdx = Character.toString(abc.charAt(r.nextInt(2)));
            cliOperationType = "put";
            cliContentID = r.nextInt(10);
            cliContentValue = r.nextInt(1000);

            if (!operation(cliServerIdx, cliOperationType, cliContentID, cliContentValue))
                break;
        }
    }

    private boolean operation(String cliServerIdx, String cliOperationType, Integer cliContentID,
                              Integer cliContentValue) throws IOException, ClassNotFoundException {
        Integer serverID;
        if (cliServerIdx.equals("a"))
            serverID = HostInfo.getInstance().HashPrimary(cliContentID);
        else if (cliServerIdx.equals("b"))
            serverID = HostInfo.getInstance().HashSecondary(cliContentID);
        else
            serverID = HostInfo.getInstance().HashTertiary(cliContentID);
        if (!cliOperationType.equals("put") && !cliOperationType.equals("get"))
            return false;
        if (cliOperationType.equals("put")) {
            this.oouts.get(serverID).writeObject(new Message(MessageType.client_put,
                    new StoredContent(cliContentID, cliContentValue), id));
            Message receivedMsg = (Message) oins.get(serverID).readObject();
            System.out.println("Set content " + cliContentID + " as " + cliContentValue + " . Result: " +
                    ((receivedMsg.getMessageType() == MessageType.client_put_ack) ? "Success" : "Fail") );
        }
        if (cliOperationType.equals("get")) {
            this.oouts.get(serverID).writeObject(new Message(MessageType.client_get,
                    new StoredContent(cliContentID), id));
            Message receivedMsg = (Message) oins.get(serverID).readObject();
            if (receivedMsg.getMessageType() == MessageType.server_unavailable)
                System.out.println("Server Unavailable.");
            else if (receivedMsg.getStoredContent() == null)
                System.out.println("Content Not exist.");
            else
                System.out.println(receivedMsg.getStoredContent().getIntegerValue());
        }
        return true;
    }

    private void establishConnection() throws IOException {
        System.out.println("Establishing connection to");
        for (Map.Entry<Integer, HostConfiguration> hc : HostInfo.getInstance().getServerNodes().entrySet()) {
            System.out.print("\tServer " + hc.getKey() + ":" + hc.getValue().getHostname() + ":" + hc.getValue().getPort() + "...");
            Socket socket = new Socket(hc.getValue().getHostname(), hc.getValue().getPort());
            this.connections.put(hc.getValue().getId(), socket);
            this.oouts.put(hc.getValue().getId(), new ObjectOutputStream(socket.getOutputStream()));
            this.oins.put(hc.getValue().getId(), new ObjectInputStream(socket.getInputStream()));
            System.out.println("\tDone. ");
        }
    }
}
