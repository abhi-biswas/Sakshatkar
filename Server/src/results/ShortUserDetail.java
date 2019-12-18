package results;

import java.io.Serializable;

/**
 * @author Abhijeet Biswas
 * This class is intended to show details of users who aren't friends yet.
 */
public class ShortUserDetail implements Serializable {
    private String username ;
    private String fname, sname,city;

    public ShortUserDetail(String username, String fname, String lname, String city) {
        this.username = username;
        this.fname = fname;
        this.sname = lname;
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public String getFname() {
        return fname;
    }

    public String getSname() {
        return sname;
    }

    public String getCity() {
        return city;
    }
}
