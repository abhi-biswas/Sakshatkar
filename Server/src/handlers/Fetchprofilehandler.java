package handlers;

import constants.FetchprofileStatus;
import mainclasses.Connector;
import requests.Fetchprofilerequest;
import results.Fetchprofileresult;

import java.sql.*;

public class Fetchprofilehandler implements Handler {
    private Connection con=Connector.getConnection();

    private Fetchprofilerequest fetchprofilerequest;
    public Fetchprofilehandler(Fetchprofilerequest fetchprofilerequest)
    {
        this.fetchprofilerequest=fetchprofilerequest;
    }
    public Fetchprofileresult handle()  {

        try {
            //System.out.print(this.fetchprofilerequest.getUsername());
            boolean isloggedin=isLoggedIn(this.fetchprofilerequest.getUsername());
            if(!isloggedin) {
                return new Fetchprofileresult(this.fetchprofilerequest.getUsername(), FetchprofileStatus.NOTLOGGEDIN);
            }
            else {
                //   System.out.print("okk");
                System.out.print(isloggedin);
                // con = Connector.getConnection();
                String query = "select * from user where username = ?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1,this.fetchprofilerequest.getUsername());
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {

                    String username=rs.getString("username");
                    String fname=rs.getString("fname");
                    String lname=rs.getString("lname");
                    String addressline1=rs.getString("addressline1");
                    String addressline2=rs.getString("addressline2");
                    String city=rs.getString("city");
                    int pincode=rs.getInt("pincode");
                    //System.out.print(username+fname+lname+addressline1+addressline2+city);
                    return new Fetchprofileresult(username,fname,lname,addressline1,addressline2,city,pincode,FetchprofileStatus.SUCESS);
                }
                else
                    return  new Fetchprofileresult(this.fetchprofilerequest.getUsername(),FetchprofileStatus.USERNOTEXIST);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Fetchprofileresult(this.fetchprofilerequest.getUsername(),FetchprofileStatus.ERROR);
        }

    }


    private boolean isLoggedIn(String username) throws SQLException {


        String query = "select * from login where username = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, this.fetchprofilerequest.getUsername());
        ResultSet rs = preparedStatement.executeQuery();
        System.out.print("ok");
        if (rs.next()) {

            return true;
        }
        return false;
    }








    }

