import java.io.Serializable;

public class Message implements Serializable {

    private MessageType messageType;
    private StoredContent storedContent;
    private Integer from;
    private VectorTime timestamp;

    public Message(MessageType messageType, StoredContent storedContent) {
        this.messageType = messageType;
        this.storedContent = storedContent;
        this.from = null;
        this.timestamp = null;
    }

    Message(MessageType messageType, StoredContent storedContent, Integer from) {
        this.messageType = messageType;
        this.storedContent = storedContent;
        this.from = from;
        this.timestamp = null;
    }

    Message(MessageType messageType, StoredContent storedContent, Integer from, VectorTime timestamp) {
        this.messageType = messageType;
        this.storedContent = storedContent;
        this.from = from;
//        this.to = to;
        this.timestamp = timestamp;
    }

    MessageType getMessageType() {
        return messageType;
    }

    StoredContent getStoredContent() {
        return storedContent;
    }

    Integer getFrom() {
        return from;
    }

    VectorTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", storedContent=" + storedContent +
                ", from=" + from +
                ", timestamp=" + timestamp +
                '}';
    }
}
