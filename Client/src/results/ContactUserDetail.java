package results;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContactUserDetail implements Serializable {
    private String username;
    private String fname, lname;
    private Timestamp lastseen;
    private Boolean isonline;

    public ContactUserDetail(String username, String fname, String lname, Timestamp lastseen){
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.lastseen = lastseen;
        this.isonline = false;
    }

    public ContactUserDetail(String username, String fname, String lname, Boolean isonline){
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.lastseen = null;
        this.isonline = isonline;
    }

    public String getUsername() {
        return username;
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }

    public Timestamp getLastseen() {
        return lastseen;
    }

    public Boolean getIsonline() {

        return  isonline;
    }
}
