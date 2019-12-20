package results;

import constants.MessageType;

import java.io.Serializable;
import java.sql.Timestamp;

//intended to be used when the client creates new message
abstract public class Message implements Serializable {
    private MessageType type;
    private String sender,receiver;
    private Timestamp timestamp;

    public Message(MessageType type, String sender, String receiver, Timestamp timestamp) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }

    public MessageType getType() {
        return type;
    }
    public String getPrefix()
    {
        return type.toString();
    }
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
