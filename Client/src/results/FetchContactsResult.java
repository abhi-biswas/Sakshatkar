package results;

import constants.BinaryStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class FetchContactsResult implements Serializable {
    private ArrayList<ContactUserDetail> list;
    private BinaryStatus binaryStatus;

    public FetchContactsResult(ArrayList<ContactUserDetail> list){
        this.list = list;
        binaryStatus = BinaryStatus.SUCCESS;
    }

    public  FetchContactsResult(BinaryStatus binaryStatus){
        this.binaryStatus = binaryStatus;
    }

    public ArrayList<ContactUserDetail> getList() {
        return list;
    }

    public BinaryStatus getBinaryStatus() {
        return binaryStatus;
    }
}
