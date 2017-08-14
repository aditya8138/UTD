import java.io.Serializable;

/**
 * Created by hanlin on 2/18/17.
 */
public class Message implements Serializable, Comparable<Message> {
    private int senderID;
    public enum type {
        request,
        ack,
        complete
    }
    private type messageType;
    private long logicalTimestamp;

    public Message(int senderID, type messageType, long logicalTimestamp) {
        this.senderID = senderID;
        this.messageType = messageType;
        this.logicalTimestamp = logicalTimestamp;
    }

    public int getSenderID() {
        return senderID;
    }

    public long getLogicalTimestamp() {
        return logicalTimestamp;
    }

    public type getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderID=" + senderID +
                ", messageType=" + messageType +
                ", logicalTimestamp=" + logicalTimestamp +
                '}';
    }

    @Override
    public int compareTo(Message message) {
        if (this.logicalTimestamp > message.getLogicalTimestamp())
            return 1;
        else if (this.logicalTimestamp < message.getLogicalTimestamp())
            return -1;
        else {
            if (this.senderID > message.getSenderID())
                return 1;
            else
                return -1;
        }
    }
}
