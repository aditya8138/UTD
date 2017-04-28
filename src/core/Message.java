package core;

/**
 * Created by hanlin on 4/27/17.
 */
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hanlin on 4/27/17.
 */
public class Message implements Serializable {

    private MessageType messageType;
    private LocalDateTime timeStamp;
    private char senderID;
    private Object content;

    public Message(MessageType messageType, LocalDateTime timeStamp, char senderID, Object content) {
        this.messageType = messageType;
        this.timeStamp = timeStamp;
        this.senderID = senderID;
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public char getSenderID() {
        return senderID;
    }

    public void setSenderID(char senderID) {
        this.senderID = senderID;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", timeStamp=" + timeStamp +
                ", senderID=" + senderID +
                ", content=" + content +
                '}';
    }
}
