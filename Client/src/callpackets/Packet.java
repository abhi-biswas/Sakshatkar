package callpackets;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author Abhijeet Biswas
 */
public abstract class Packet implements Serializable {
    byte[] data;
    Timestamp timestamp;
    private String sendername,receivername;

    public Packet(byte[] data, Timestamp timestamp, String sendername, String receivername) {
        this.data = data;
        this.timestamp = timestamp;
        this.sendername = sendername;
        this.receivername = receivername;
    }

    public byte[] getData() {
        return data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getSendername() {
        return sendername;
    }

    public String getReceivername() {
        return receivername;
    }
}
