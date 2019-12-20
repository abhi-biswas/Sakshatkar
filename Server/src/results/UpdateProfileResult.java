package results;

import constants.UpdateProfileStatus;
import requests.UpdateProfileRequest;

public class UpdateProfileResult {

    private String username;
    private UpdateProfileStatus updateProfileStatus;
    public UpdateProfileResult(String username, UpdateProfileStatus updateProfileStatus)
    {

        this.username=username;
        this.updateProfileStatus=updateProfileStatus;
    }

    public String getUsername(){
        return this.username;
    }
    public UpdateProfileStatus getUpdateProfileStatus(){ return this.updateProfileStatus;}
}
