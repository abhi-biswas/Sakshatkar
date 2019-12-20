package results;

import constants.MessageType;

import java.sql.Timestamp;

public class ImageMessage extends Message {
    private byte imagedata[];

    public ImageMessage( String sender, String receiver, Timestamp timestamp, byte[] imagedata) {
        super(MessageType.IMAGE, sender, receiver, timestamp);
        this.imagedata = imagedata;
    }

    public byte[] getImagedata() {
        return imagedata;
    }
}
