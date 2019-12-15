package requests;

import java.io.Serializable;

public class SendFriendRequest implements Serializable {
    private String senderName,receiverName;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public SendFriendRequest(String senderName, String receiverName) {
        this.senderName = senderName;
        this.receiverName = receiverName;
    }
}
