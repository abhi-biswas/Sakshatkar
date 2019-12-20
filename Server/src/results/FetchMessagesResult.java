package results;

import java.io.Serializable;
import java.util.ArrayList;

public class FetchMessagesResult implements Serializable {
    ArrayList<Message> list;

    public ArrayList<Message> getList() {
        return list;
    }

    public FetchMessagesResult(ArrayList<Message> list) {
        this.list = list;
    }
}
