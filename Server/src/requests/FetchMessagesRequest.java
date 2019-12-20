package requests;

import java.io.Serializable;

public class FetchMessagesRequest implements Serializable {
   private String sendername,receivername;

    public FetchMessagesRequest(String sendername, String receivername) {
        this.sendername = sendername;
        this.receivername = receivername;
    }

    public String getSendername() {
        return sendername;
    }

    public String getReceivername() {
        return receivername;
    }
}
