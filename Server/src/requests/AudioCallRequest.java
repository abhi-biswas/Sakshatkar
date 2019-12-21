package requests;

public class AudioCallRequest extends CallRequest {

    private String caller,callee;
    private  int port;
    public AudioCallRequest(String caller,String  callee)
    {
        this.callee=callee;
        this.caller=caller;
        this.port=3002;
    }


}

