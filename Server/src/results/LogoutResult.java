package results;

import constants.LogoutStatus;

public class LogoutResult {
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
