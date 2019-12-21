package results;

import constants.BinaryStatus;
import java.io.Serializable;

public class GroupCreationResult implements Serializable{
    private int groupid;
    BinaryStatus binaryStatus;

    public GroupCreationResult(int groupid){
        this.groupid = groupid;
        this.binaryStatus = BinaryStatus.SUCCESS;
    }

    public  GroupCreationResult(BinaryStatus binaryStatus){
        this.binaryStatus = binaryStatus;
    }

    public BinaryStatus getBinaryStatus() {
        return binaryStatus;
    }

    public int getGroupid() {
        return groupid;
    }
}
