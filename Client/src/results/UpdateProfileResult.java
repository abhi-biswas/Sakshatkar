package results;

import constants.UpdateProfileStatus;

public class UpdateProfileResult {

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
