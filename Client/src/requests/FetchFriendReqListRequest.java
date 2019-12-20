package requests;

import java.io.Serializable;

public class FetchFriendReqListRequest implements Serializable {
    private String username;

    public FetchFriendReqListRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
