package results;

import constants.*;

public class Loginresult {
    private Loginstatus loginstatus;
    private String username;
    private String fname;
    private String lname;

    public Loginresult(Loginstatus loginstatus, String username, String fname, String lname){
        this.loginstatus = loginstatus;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
    }

    public Loginresult(Loginstatus loginstatus){
        this.loginstatus = loginstatus;
        this.username = this.fname = this.lname = null;
    }

    public Loginstatus getLoginstatus() {
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

