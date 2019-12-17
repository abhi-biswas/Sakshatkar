package requests;

import java.io.Serializable;

public class FetchContactsRequest implements Serializable {
    private String username;

    public FetchContactsRequest(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
