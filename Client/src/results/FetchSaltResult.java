package results;

import constants.FetchSaltStatus;

import java.io.Serializable;

public class FetchSaltResult implements Serializable {
    String salt;
    FetchSaltStatus status;

    public FetchSaltResult(String salt) {
        this.salt = salt;
        status = FetchSaltStatus.SUCCESS;
    }

    public FetchSaltResult(FetchSaltStatus status) {
        this.status = status;
        salt=null;
    }

    public String getSalt() {
        return salt;
    }

    public FetchSaltStatus getStatus() {
        return status;
    }
}
