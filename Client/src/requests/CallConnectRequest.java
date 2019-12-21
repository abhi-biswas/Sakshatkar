package requests;

import constants.CallType;

public class CallConnectRequest {
    private String callername, calleename;
    private CallType callType;

    public CallConnectRequest(String callername, String calleename, CallType callType){
        this.callername = callername;
        this.calleename = calleename;
        this.callType = callType;
    }

    public String getCallername() {
        return callername;
    }

    public String getCalleename() {
        return calleename;
    }

    public CallType getCallType() {
        return callType;
    }
}
