package results;

import constants.LogoutStatus;

import java.io.Serializable;

public class LogoutResult implements Serializable {
    LogoutStatus logoutStatus = LogoutStatus.FAILED;

    public LogoutResult(LogoutStatus logoutStatus){
        this.logoutStatus = logoutStatus;
    }

    public LogoutStatus getLogoutStatus() {
        return logoutStatus;
    }

    public void setLogoutStatus(LogoutStatus logoutStatus) {
        this.logoutStatus = logoutStatus;
    }
}
