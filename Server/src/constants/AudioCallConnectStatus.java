package constants;

import java.io.Serializable;

public enum AudioCallConnectStatus implements Serializable {
    CALLEEOFFLINE,
    CALLEEBUSY,
    CONNECTSUCCESSFUL,
    CONNECTFAILURE,
    CALLREJECTED
}
