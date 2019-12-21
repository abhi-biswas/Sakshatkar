package results;

import constants.CallStatus;

public class CallResult {
    private CallStatus callStatus;
    private  String caller,callee;
    public CallResult(String caller,String callee,CallStatus callStatus)
    {
        this.callee=callee;
        this.callStatus=callStatus;
        this.caller=caller;
    }

    public String getCallee() {
        return callee;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public String getCaller() {
        return caller;
    }
}
