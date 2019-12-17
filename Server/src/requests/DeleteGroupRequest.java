package requests;

import java.io.Serializable;

public class DeleteGroupRequest implements Serializable {

    private int groupid;

    public DeleteGroupRequest(int groupid){
        this.groupid = groupid;
    }

    public int getGroupid() {
        return groupid;
    }
}
