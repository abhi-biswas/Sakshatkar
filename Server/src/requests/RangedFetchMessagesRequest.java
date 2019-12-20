package requests;
// intended to be used for efficient chat checking and refreshing on clients side
public class RangedFetchMessagesRequest extends  FetchMessagesRequest{
    private int range; // in miliseconds

    public RangedFetchMessagesRequest(String username, int range) {
        super(username);
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
