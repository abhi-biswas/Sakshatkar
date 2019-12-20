package requests;

import java.io.Serializable;

public class SearchUserRequest implements Serializable {

    private String username;
    private String ip;
    public SearchUserRequest(String username)
    {
        this.username=username;
    }
    public String getUsername(){return this.username;}





}
