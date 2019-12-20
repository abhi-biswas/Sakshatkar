package handlers;

import constants.LogoutStatus;
import mainclasses.Connector;
import requests.LogoutRequest;
import results.LogoutResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogoutHandler implements Handler {
    LogoutRequest logoutRequest = null;
    Connection connection = null;

    public LogoutHandler(LogoutRequest logoutRequest){
        this.logoutRequest = logoutRequest;
    }

    public LogoutResult handle(){
            return  logoutUser();
    }

    public LogoutResult logoutUser(){

        try {
            connection = Connector.getConnection();

            String query = "delete from login where username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,this.logoutRequest.getUsername());

            preparedStatement.executeUpdate();

            query = "update user set lastseen = CURRENT_TIMESTAMP  where username = ?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,this.logoutRequest.getUsername());

            preparedStatement.executeUpdate();

            return new LogoutResult(LogoutStatus.SUCCESS);

        }catch (SQLException e){
            e.printStackTrace();

            return new LogoutResult(LogoutStatus.FAILED);
        }

    }
}
