package handlers;
import constants.SearchUserStatus;
import mainclasses.Connector;
import requests.SearchUserRequest;
import results.SearchUserResult;
import results.ShortUserDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchUserHandler implements Handler {
    private SearchUserRequest searchUserRequest;
    public SearchUserHandler(SearchUserRequest searchUserRequest)
    {
        this.searchUserRequest=searchUserRequest;
    }
    private Connection  con=Connector.getConnection();

    public SearchUserResult handle() {
        try
        {
            String query="select * from user where username=?";
            PreparedStatement preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,this.searchUserRequest.getUsername());
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next())
            {
                return new SearchUserResult(new ShortUserDetail(this.searchUserRequest.getUsername(),rs.getString("fname"),rs.getString("lname"),rs.getString("city")), SearchUserStatus.FOUND);
            }
            else
            {
                return new SearchUserResult(new ShortUserDetail("NA","NA","NA","NA"), SearchUserStatus.NOTFOUND);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SearchUserResult(SearchUserStatus.ERROR);
        }





    }


















}
