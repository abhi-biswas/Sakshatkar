package handlers;

import constants.FetchprofileStatus;
import mainclasses.Connector;
import requests.Fetchprofilerequest;
import results.Fetchprofileresult;

import java.sql.*;

public class Fetchprofilehandler {

    private Fetchprofilerequest fetchprofilerequest;
    public Fetchprofilehandler(Fetchprofilerequest fetchprofilerequest)
    {
        this.fetchprofilerequest=fetchprofilerequest;
    }
    public Fetchprofileresult handle() throws SQLException {
        boolean isloggedin=isLoggedIn(this.fetchprofilerequest.getUsername());
        if(!isloggedin) {
            return new Fetchprofileresult(this.fetchprofilerequest.getUsername(), FetchprofileStatus.NOTLOGGEDIN);
        }
        else {
            Connection con = Connector.getConnection();
            String query = "select *from user where username= ?";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1,this.fetchprofilerequest.getUsername());

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {

                String username=rs.getString("username");
                String fname=rs.getString("fname");
                String lname=rs.getString("lname");
                String addressline1=rs.getString("addressline1");
                String addressline2=rs.getString("addressline2");
                String city=rs.getString("city");
                int pincode=rs.getInt("pincode");
                return new Fetchprofileresult(username,fname,lname,addressline1,addressline2,city,pincode,FetchprofileStatus.SUCESS);
            }
            else
            return  new Fetchprofileresult(this.fetchprofilerequest.getUsername(),FetchprofileStatus.USERNOTEXIST);
        }
    }


    private boolean isLoggedIn(String username) throws SQLException {
        Connection con = Connector.getConnection();
        String query = "select *from login where username= ?";

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1,this.fetchprofilerequest.getUsername());

        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {

            return true;
        }
        return false;
    }








    }

