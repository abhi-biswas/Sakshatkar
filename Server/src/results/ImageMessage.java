package results;

import constants.MessageType;

import java.sql.Timestamp;

public class ImageMessage extends Message {
    private Byte imagedata[];

    public ImageMessage(MessageType type, String sender, String receiver, Timestamp timestamp, Byte[] imagedata) {
        super(type, sender, receiver, timestamp);
        this.imagedata = imagedata;
    }

    public Byte[] getImagedata() {
        return imagedata;
    }
}
