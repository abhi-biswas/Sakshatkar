package requests;

import java.io.Serializable;

public class SignUpRequest implements Serializable {
    private String username,fname,lname,password,addressline1,addressline2,city;
    int pincode;

    public SignUpRequest(String username, String fname, String lname, String password, String addressline1, String addressline2, String city, int pincode) {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.city = city;
        this.pincode = pincode;
    }

    public String getUsername() {
        return username;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPassword() {
        return password;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public String getCity() {
        return city;
    }

    public int getPincode() {
        return pincode;
    }
}
