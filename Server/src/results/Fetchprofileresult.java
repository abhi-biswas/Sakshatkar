package results;

import constants.FetchprofileStatus;

public class Fetchprofileresult {
    private FetchprofileStatus fetchprofileStatus;
    private String username;
    private String fname;
    private String lname;
    private String addressline1;
    private String addressline2;
    private String city;
    int pincode;
    public Fetchprofileresult(String username,FetchprofileStatus status)
    {
        this.fetchprofileStatus=status;
        this.username=username;

        this.fname=this.lname=this.addressline1=this.addressline2=this.city=null;
        this.pincode=0;
    }
    public Fetchprofileresult(String username,String fname,String lname,String addressline1,String addressline2,String city,int pincodeFetchprofile,FetchprofileStatus fetchprofileStatus)
    {
        this.fetchprofileStatus=fetchprofileStatus;
        this.username=username;
        this.fname=fname;
        this.lname=lname;
        this.addressline1=addressline1;
        this.addressline2=addressline2;
        this.city=city;
        this.pincode=pincode;


    }
}




    public FetchprofileStatus getLoginstatus() {
        return fetchprofileStatus;
    }
    public String getUsername() {
        return username;
    }
}

