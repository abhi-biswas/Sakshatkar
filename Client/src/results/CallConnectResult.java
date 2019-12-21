package results;

import constants.CallConnectStatus;
import constants.CallType;

import java.io.Serializable;

public class CallConnectResult implements Serializable {
    private CallType callType;
    private CallConnectStatus callConnectStatus;

    public CallConnectResult(CallType callType){
        this.callType = callType;
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
}
