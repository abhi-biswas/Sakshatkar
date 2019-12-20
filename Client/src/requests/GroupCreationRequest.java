package requests;

public class GroupCreationRequest {
    private String ownername;
    private String groupname;
    private int groupcapacity;

    public GroupCreationRequest(String ownername, String groupname, int groupcapacity){
        this.groupcapacity = groupcapacity;
        this.groupname = groupname;
        this.ownername = ownername;
    }

    public int getGroupcapacity() {
        return groupcapacity;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getOwnername() {
        return ownername;
    }
}
