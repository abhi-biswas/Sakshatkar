package results;

import constants.MessageType;

import java.sql.Timestamp;

public class VideoMessage extends Message {
    private byte videodata[];

    public VideoMessage( String sender, String receiver, Timestamp timestamp, byte[] videodata) {
        super(MessageType.VIDEO, sender, receiver, timestamp);
        this.videodata = videodata;
    }

    public byte[] getVideodata() {
        return videodata;
    }
}
