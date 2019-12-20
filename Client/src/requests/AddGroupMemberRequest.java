package requests;


import java.io.Serializable;

public class AddGroupMemberRequest implements Serializable {
    private int groupid;
    private String username;

    public AddGroupMemberRequest(int groupid, String username){
        this.groupid = groupid;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getGroupid() {
        return groupid;
    }
}
