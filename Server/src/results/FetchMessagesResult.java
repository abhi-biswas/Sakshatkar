package results;

import constants.FetchMessagesStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class FetchMessagesResult implements Serializable {
    ArrayList<Message> list;
    FetchMessagesStatus status;
    public ArrayList<Message> getList() {
        return list;
    }

    public FetchMessagesResult(ArrayList<Message> list) {
        this.list = list;
        status = FetchMessagesStatus.SUCCESS;
    }

    public FetchMessagesResult(FetchMessagesStatus status) {
        this.status = status;
    }

    public FetchMessagesStatus getStatus() {
        return status;
    }
}
