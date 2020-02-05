package results;

import constants.CallConnectStatus;
import constants.CallType;

import java.io.Serializable;

public class CallConnectResult implements Serializable {
    private CallType callType;
    private CallConnectStatus callConnectStatus;
    private String caller,callee;

    public CallConnectResult(CallType callType,String caller,String callee){
        this.callType = callType;
        this.callee = callee;
        this.caller = caller;
        this.callConnectStatus = CallConnectStatus.CONNECTSUCCESSFUL;
    }

    public CallConnectResult(CallConnectStatus callConnectStatus)
    {
        this.callConnectStatus = callConnectStatus;
    }

    public CallType getCallType() {
        return callType;
    }

    public CallConnectStatus getCallConnectStatus() {
        return callConnectStatus;
    }

    public String getCallee() {
        return callee;
    }

    public String getCaller() {
        return caller;
    }
}
