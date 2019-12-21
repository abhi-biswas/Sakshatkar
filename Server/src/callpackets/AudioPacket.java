package callpackets;

import java.sql.Timestamp;
/**
 * @author Abhijeet Biswas
 */
public class AudioPacket extends Packet {
    boolean isMute;

    public AudioPacket(byte[] data, Timestamp timestamp, String sendername, String receivername, boolean isMute) {
        super(data, timestamp, sendername, receivername);
        this.isMute = isMute;
    }

    public boolean isMute() {
        return isMute;
    }
}
