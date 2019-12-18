package requests;

public class CountFetchMessagesRequest extends FetchMessagesRequest {
    private int count;

    public CountFetchMessagesRequest(String username) {
        super(username);
        count = 15; // default
    }

    public CountFetchMessagesRequest(String username, int count) {
        super(username);
        this.count = count;
    }
}
