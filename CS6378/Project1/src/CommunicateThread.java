import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Created by hanlin on 2/22/17.
 */
public class CommunicateThread implements Runnable {

    /* Indicating the node id of the other side. */
    private int otherid;

    private Socket socket;

    ObjectOutputStream oos;
    ObjectInputStream ois;

    Node node;

    public enum ChannelStatus {
        waiting_req, // when initial connection request was made by the other.
        ready_to_send_req, // when initial connection request was made by itself.
        waiting_ack, // when request was sent, waiting for ack from the other.
        ready_to_send_ack,
        complete,
        ending
    }

    private ChannelStatus channelStatus;


    public CommunicateThread(int id, Socket socket, Node node) {
        this.otherid = id;
        this.socket = socket;
        this.node = node;
        this.channelStatus = (id > node.getId()) ? ChannelStatus.waiting_req : ChannelStatus.ready_to_send_req;
    }

    @Override
    public synchronized void run() {
        System.out.println(this.socket);
        System.out.println("Local IP " + this.socket.getLocalSocketAddress() + "Remote IP: "+ this.socket.getRemoteSocketAddress());
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());

            boolean flag = true;

            while (flag) {
                switch (this.channelStatus) {
                    case waiting_req:
                        Object object;
                        try {
                            object = this.ois.readObject();
                            if (object != null) {
                                Message request = (Message) object;
                                this.node.getLogicalClock().logicalTimeIncrease(this.node.getId(), request.getLogicalTimestamp());

                                if (this.node.checkWhetherReplyAck(request.getLogicalTimestamp(), request.getSenderID())) {
                                    this.sendACK();
                                    if (this.node.getPhase() < 3)
                                        this.channelStatus = ChannelStatus.ready_to_send_req;
                                    else
                                        this.channelStatus = ChannelStatus.complete;
                                } else {
                                    this.node.withholdAck(request.getSenderID());
                                    this.channelStatus = ChannelStatus.ready_to_send_ack;
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case ready_to_send_req:
                        synchronized (this) {
                            try {
                                while(!this.node.getMessageList(this.otherid)) {
                                    wait(1);

                                    if (this.node.getPhase() == 3) {
                                        this.channelStatus = ChannelStatus.complete;
                                        break;
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        this.sendRequest();
                        channelStatus = ChannelStatus.waiting_ack;
                        break;
                    case waiting_ack:
                        try {
                            object = this.ois.readObject();

                            if (object != null) {
                                Message ack = (Message) object;
                                if (ack.getMessageType() != Message.type.ack)
                                this.node.getLogicalClock().logicalTimeIncrease(this.node.getId(), ack.getLogicalTimestamp());
                                this.node.receiveAck(ack.getSenderID());
                                if (this.node.getPhase() < 3)
                                    this.channelStatus = ChannelStatus.waiting_req;
                                else
                                    this.channelStatus = ChannelStatus.complete;
                            } else {
                                System.out.println("Could not read a object from socket.");
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case ready_to_send_ack:
                        synchronized (this) {
                            try {
                                while (!this.node.checkWithholdAck()) {
                                    wait(5 + new Random().nextInt(5));
                                    if (this.node.getPhase() == 3) {
                                        this.channelStatus = ChannelStatus.complete;
                                        break;
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!this.node.checkWithholdAck(this.otherid))
                            System.err.println("There is no pending ack for node " + this.otherid);
                        this.sendACK();
                        if (this.node.getPhase() < 3)
                            this.channelStatus = ChannelStatus.ready_to_send_req;
                        else
                            this.channelStatus = ChannelStatus.complete;
                        break;
                    case complete:
                        if (!this.node.getPendingAckList()[this.otherid]) {
                            try {
                                object = this.ois.readObject();
                                if (object != null) {
                                    Message request = (Message) object;
                                    this.node.getLogicalClock().logicalTimeIncrease(this.node.getId(), request.getLogicalTimestamp());

                                    this.sendACK();
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        this.channelStatus = ChannelStatus.ending;
                        break;
                    case ending:
                        flag = false;
                        break;
                }
            }
            wait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() throws IOException {

        this.node.getLogicalClock().logicalTimeIncrease(this.node.getId());

        Message message = new Message(this.node.getId(), Message.type.request, this.node.getCurrentRequestTimestamp());

        this.oos.writeObject(message);

        node.deliverMessageList(this.otherid);
        node.addRequestMessageSent();
        node.addCurRequestMessageSent();

        this.node.getLogicalClock().logicalTimeIncrease(this.node.getId());


    }

    private void sendACK() throws IOException {
        this.node.getLogicalClock().logicalTimeIncrease(this.node.getId());
        Message ack = new Message(this.node.getId(), Message.type.ack, this.node.getLogicalClock().getLogicalTime());
        this.oos.writeObject(ack);
        node.deliverAck(this.otherid);
        node.addAckMessageSent();

        this.node.getLogicalClock().logicalTimeIncrease(this.node.getId());
    }


    @Override
    public String toString() {
        return "CommunicateThread{" +
                "\notherid=" + otherid +
                ", \nsocket=" + socket +
                ", \noos=" + oos +
                ", \nois=" + ois +
                ", \nnode=" + node +
                ", \nchannelStatus=" + channelStatus +
                "\n}";
    }
}
