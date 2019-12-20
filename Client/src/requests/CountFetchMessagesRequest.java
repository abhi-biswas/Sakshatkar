package requests;

public class CountFetchMessagesRequest extends FetchMessagesRequest {
    private int count;


    public CountFetchMessagesRequest(String sendername, String receivername, int count) {
        super(sendername, receivername);
        this.count = count;
    }

    public CountFetchMessagesRequest(String sendername, String receivername) {
        super(sendername, receivername);
        count = 15; // default
    }

    public int getCount() {
        return count;
    }
}
