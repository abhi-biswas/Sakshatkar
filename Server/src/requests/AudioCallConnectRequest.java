package requests;

public class AudioCallConnectRequest {
    private String callername, calleename;

    public AudioCallConnectRequest(String callername, String calleename){
        this.callername = callername;
        this.calleename = calleename;
    }

    public String getCallername() {
        return callername;
    }

    public String getCalleename() {
        return calleename;
    }
}
