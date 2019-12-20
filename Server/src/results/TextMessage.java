package results;

import constants.MessageType;

import java.sql.Timestamp;

public class TextMessage extends Message {
    String content;

    public TextMessage( String sender, String receiver, Timestamp timestamp, String content) {
        super(MessageType.TEXT, sender, receiver, timestamp);
        this.content = content;
    }

    public String getTextData() {
        return content;
    }
}
