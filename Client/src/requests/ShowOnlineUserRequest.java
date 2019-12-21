package requests;

import java.io.Serializable;

public class ShowOnlineUserRequest implements Serializable{
    private String username;
    private String ip;
    public ShowOnlineUserRequest(String username,String ip)
    {
        this.username=username;
        this.ip=ip;
    }
    public String getUsername()
    {return this.username;};
    public String getIp(){return this.ip;}

}
