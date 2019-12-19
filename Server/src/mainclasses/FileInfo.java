package mainclasses;

public class FileInfo {
    private int fileid;
    private String filepath;

    public FileInfo(int fileid, String filepath){
        this.fileid = fileid;
        this.filepath = filepath;
    }

    public int getFileid() {
        return fileid;
    }

    public String getFilepath() {
        return filepath;
    }
}
