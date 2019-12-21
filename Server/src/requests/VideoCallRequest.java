package requests;

public class VideoCallRequest extends CallRequest {

    private String caller,callee;
    private  int port;
    public VideoCallRequest(String caller,String  callee)
    {
        this.callee=callee;
        this.caller=caller;
        this.port=3001;
    }


}
