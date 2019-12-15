package results;

import constants.*;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private LoginStatus loginstatus;
    private String username;
    private String fname;
    private String lname;

    public LoginResult(LoginStatus loginstatus, String username, String fname, String lname){
        this.loginstatus = loginstatus;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
    }

    public LoginResult(LoginStatus loginstatus){
        this.loginstatus = loginstatus;
        this.username = this.fname = this.lname = null;
    }

    public LoginStatus getLoginstatus() {
        return loginstatus;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getUsername() {
        return username;
    }
}

