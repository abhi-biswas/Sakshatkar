package callpackets;

import java.sql.Timestamp;
/**
 * @author Abhijeet Biswas
 */
public class VideoPacket extends Packet {
    boolean isBlank;

    public boolean isBlank() {
        return isBlank;
    }

    public VideoPacket(byte[] data, Timestamp timestamp, String sendername, String receivername, boolean isBlank) {
        super(data, timestamp, sendername, receivername);
        this.isBlank = isBlank;
    }
}
