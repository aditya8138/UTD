package core;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private MessageType messageType;
    private LocalDateTime timeStamp;
    private char senderID;
    private Object content;

    public Message(MessageType messageType, LocalDateTime timeStamp, char senderID) {
        this.messageType = messageType;
        this.timeStamp = timeStamp;
        this.senderID = senderID;
        this.content = null;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public char getSenderID() {
        return senderID;
    }

    Object getContent() {
        return content;
    }

    void setContent(Object content) {
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
