package results;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContactUserDetail implements Serializable {
    private String username;
    private String fname, lname;

    public ContactUserDetail(String username, String fname, String lname){
        this.username = username;
        this.fname = fname;
        this.lname = lname;
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


}
