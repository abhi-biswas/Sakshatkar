package mainclasses;

import constants.BinaryStatus;

public class FileInfo {
    private int fileid;
    private String filepath;
    private BinaryStatus binaryStatus;

    public FileInfo(int fileid, String filepath){
        this.fileid = fileid;
        this.filepath = filepath;
        binaryStatus = BinaryStatus.SUCCESS;
    }

    public FileInfo(BinaryStatus binaryStatus){
        this.binaryStatus = binaryStatus;
    }

    public int getFileid() {
        return fileid;
    }

    public String getFilepath() {
        return filepath;
    }

    public BinaryStatus getBinaryStatus() {
        return binaryStatus;
    }
}

