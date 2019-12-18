package requests;

import java.io.Serializable;

public class FetchMessagesRequest implements Serializable {
   private String username;

    public FetchMessagesRequest(String username) {
        this.username = username;
    }
}
