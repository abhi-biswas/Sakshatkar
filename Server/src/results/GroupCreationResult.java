package results;

import constants.BinaryStatus;

public class GroupCreationResult {
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
