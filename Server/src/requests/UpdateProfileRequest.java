package requests;

import java.io.Serializable;

public class UpdateProfileRequest implements Serializable {
    private String username;
    private String fname;
    private  String lname;
    private String password;
    private String addressline1;
    private String addressline2;
    private String city;
    private  int pincode;
    public  UpdateProfileRequest(String username,String fname,String lname,String password,String addressline1,String addressline2,String city,int pincode)
    {
        this.username=username;
        this.fname=fname;
        this.lname=lname;
        this.addressline1=addressline1;
        this.addressline2=addressline2;
        this.city=city;
        this.pincode=pincode;

    }
    public String getUsername()
    {
        return this.username;
    }
    public String getFname(){ return this.fname;
    }
    public String getLname(){return this.lname;}
    public String getPassword(){return  this.password;}
    public String getAddressline1(){return this.addressline1;}
    public String getAddressline2(){return this.addressline2;}

    public String getCity(){return this.city;}
    public int getPincode(){return this.pincode;}




}
