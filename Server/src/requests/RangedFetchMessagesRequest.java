package requests;
// intended to be used for efficient chat checking and refreshing on clients side
public class RangedFetchMessagesRequest extends  FetchMessagesRequest{
    private long range; // in miliseconds

    public RangedFetchMessagesRequest(String username, long range) {
        super(username);
        this.range = range;
    }
}
