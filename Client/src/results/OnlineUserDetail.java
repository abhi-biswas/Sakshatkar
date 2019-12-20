package results;

import java.io.Serializable;

public class OnlineUserDetail implements Serializable {
    private String username;
    private String fname, lname;


   public OnlineUserDetail(String username,String fname,String lname)
   {
       this.username=username;
       this.lname=lname;
       this.fname=fname;
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
