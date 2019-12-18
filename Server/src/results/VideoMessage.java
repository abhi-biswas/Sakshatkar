package results;

import constants.MessageType;

import java.sql.Timestamp;

public class VideoMessage extends Message {
    private Byte videodata[];

    public VideoMessage(MessageType type, String sender, String receiver, Timestamp timestamp, Byte[] videodata) {
        super(type, sender, receiver, timestamp);
        this.videodata = videodata;
    }

    public Byte[] getVideodata() {
        return videodata;
    }
}
