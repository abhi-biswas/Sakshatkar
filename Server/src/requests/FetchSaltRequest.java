package requests;

import java.io.Serializable;

public class FetchSaltRequest implements Serializable {
    String username;

    public FetchSaltRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
