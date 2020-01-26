package results;

import constants.*;

import java.io.Serializable;

public class SignUpResult implements Serializable {
    private SignUpStatus signUpStatus;


    public SignUpResult(SignUpStatus signUpStatus){
        this.signUpStatus = signUpStatus;

    }


    public SignUpStatus getSignUpStatus() {
        return this.signUpStatus;
    }

}

