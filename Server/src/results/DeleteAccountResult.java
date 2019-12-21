package results;

import constants.DeleteAccountStatus;

import java.io.Serializable;

public class DeleteAccountResult implements Serializable {
    private  String username;
    private DeleteAccountStatus deleteAccountStatus;
    public DeleteAccountResult(String username,DeleteAccountStatus deleteAccountStatus){
        this.username=username;
        this.deleteAccountStatus=deleteAccountStatus;
    }

    public DeleteAccountStatus getDeleteAccountStatus() {
        return deleteAccountStatus;
    }

    public String getUsername() {
        return username;
    }
}
