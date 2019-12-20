package results;

import constants.LoginStatus;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private LoginStatus loginstatus;
    private String username;
    private String fname;
    private String lname;
    private int ugroupid;

    public LoginResult(LoginStatus loginstatus, String username, String fname, String lname, int ugroupid){
        this.loginstatus = loginstatus;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.ugroupid = ugroupid;
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

    public int getUgroupid() {
        return ugroupid;
    }
}

