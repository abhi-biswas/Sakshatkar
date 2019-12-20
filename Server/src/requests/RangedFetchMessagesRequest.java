package requests;
// intended to be used for efficient chat checking and refreshing on clients side
public class RangedFetchMessagesRequest extends  FetchMessagesRequest{
    private int range; // in seconds

    public RangedFetchMessagesRequest(String sendername, String receivername, int range) {
        super(sendername, receivername);
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
