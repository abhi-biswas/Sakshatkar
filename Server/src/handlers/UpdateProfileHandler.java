package handlers;

import constants.UpdateProfileStatus;
import mainclasses.Connector;
import requests.UpdateProfileRequest;
import results.UpdateProfileResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProfileHandler implements Handler {

    private Connection con =Connector.getConnection();
    private  UpdateProfileRequest updateProfileRequest;
    public UpdateProfileHandler(UpdateProfileRequest updateprofilerequest)
    {
        this.updateProfileRequest=updateprofilerequest;
    }
    public UpdateProfileResult handle() {

       try
       {
           //this.updateProfileRequest.getUsername();
           boolean isloggedin=isLoggedIn(this.updateProfileRequest.getUsername());
           if(!isloggedin)
           {
               return new UpdateProfileResult(this.updateProfileRequest.getUsername(), UpdateProfileStatus.NOTLOGGEDIN);
           }
           else {
               String query = "update user set fname=? ,lname=?,password=?,addressline1 = ? ,addressline2 = ? ,city =? ,pincode = ? where username=? ";
               PreparedStatement preparedStatement = con.prepareStatement(query);
               preparedStatement.setString(1, this.updateProfileRequest.getFname());
               preparedStatement.setString(2, this.updateProfileRequest.getLname());
               preparedStatement.setString(3, this.updateProfileRequest.getPassword());
               preparedStatement.setString(4, this.updateProfileRequest.getAddressline1());
               preparedStatement.setString(5, this.updateProfileRequest.getAddressline2());
               preparedStatement.setString(6, this.updateProfileRequest.getCity());
               preparedStatement.setInt(7,this.updateProfileRequest.getPincode());
               preparedStatement.setString(8,this.updateProfileRequest.getUsername());
               preparedStatement.executeUpdate();
               return new UpdateProfileResult(this.updateProfileRequest.getUsername(), UpdateProfileStatus.SUCESS);


           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return  new UpdateProfileResult(UpdateProfileStatus.FAILURE);
       }

    }
    private boolean isLoggedIn(String username) throws SQLException {


        String query = "select * from login where username = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, this.updateProfileRequest.getUsername());
        ResultSet rs = preparedStatement.executeQuery();
      //  System.out.print("ok");
        if (rs.next()) {

            return true;
        }
        return false;
    }






}
