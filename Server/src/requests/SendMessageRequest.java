package requests;

import constants.MessageType;
import results.Message;

import java.io.Serializable;
import java.sql.Timestamp;
//for sending a new message
public class SendMessageRequest implements Serializable {
    Message message;

    public SendMessageRequest(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
