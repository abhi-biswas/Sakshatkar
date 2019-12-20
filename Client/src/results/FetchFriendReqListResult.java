package results;

import constants.BinaryStatus;

import java.util.ArrayList;

public class FetchFriendReqListResult {
    ArrayList<ShortUserDetail> list;
    BinaryStatus status;

    public FetchFriendReqListResult(ArrayList<ShortUserDetail> list) {
        this.list = list;
        status = BinaryStatus.SUCCESS;
    }

    public FetchFriendReqListResult(BinaryStatus status) {
        this.status = status;
    }

    public ArrayList<ShortUserDetail> getList() {
        return list;
    }

    public BinaryStatus getStatus() {
        return status;
    }
}
