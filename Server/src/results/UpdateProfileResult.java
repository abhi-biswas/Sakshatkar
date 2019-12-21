package results;

import constants.UpdateProfileStatus;
import requests.UpdateProfileRequest;

import java.io.Serializable;

public class UpdateProfileResult implements Serializable {

    private String username;
    private UpdateProfileStatus updateProfileStatus;
    public UpdateProfileResult(String username, UpdateProfileStatus updateProfileStatus)
    {

        this.username=username;
        this.updateProfileStatus=updateProfileStatus;
    }

    public UpdateProfileResult(UpdateProfileStatus updateProfileStatus) {
        this.updateProfileStatus = updateProfileStatus;
    }

    public String getUsername(){
        return this.username;
    }
    public UpdateProfileStatus getUpdateProfileStatus(){ return this.updateProfileStatus;}
}
