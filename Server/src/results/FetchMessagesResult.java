package results;

import constants.FetchMessageStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class FetchMessagesResult implements Serializable {
    ArrayList<Message> list;
    FetchMessageStatus status;
    public ArrayList<Message> getList() {
        return list;
    }

    public FetchMessagesResult(ArrayList<Message> list) {
        this.list = list;
        status = FetchMessageStatus.SUCCESS;
    }

    public FetchMessagesResult(FetchMessageStatus status) {
        this.status = status;
    }

    public FetchMessageStatus getStatus() {
        return status;
    }
}
