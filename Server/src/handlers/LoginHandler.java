package handlers;

import mainclasses.Connector;
import constants.LoginStatus;
import requests.LoginRequest;
import results.LoginResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class LoginHandler implements Handler {
    private LoginRequest loginrequest;
    private Connection connection = null;
    private String ip = null;


    public LoginHandler(LoginRequest loginrequest, String ip){
        this.loginrequest = loginrequest;
        this.ip = ip;
    }
    public LoginResult handle()
    {
        try {
            return checkCredentials();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResult(LoginStatus.ERROR);
        }
    }
    public LoginResult checkCredentials() throws Exception{


        connection = Connector.getConnection();

        String query = "select * from user where username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.loginrequest.getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
                if(loginrequest.getPassword().equals(resultSet.getString("password")))
                    return getDetails(LoginStatus.SUCCESS);
                else
                    return getDetails(LoginStatus.PASSWORDNOTMATCHED);
        }
        else{
            return getDetails(LoginStatus.USERNOTEXIST);
        }
    }

    public LoginResult getDetails(LoginStatus loginstatus) throws Exception {

        switch (loginstatus) {

            case SUCCESS:
                String query = "select * from user where username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,this.loginrequest.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                LoginResult loginresult = null;


                if(resultSet.next()) {
                    loginresult = new LoginResult(loginstatus, this.loginrequest.getUsername(),
                            resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getInt("ugroupid"));

                    String query1 = "insert into login values(?, ?, ?)";

                    preparedStatement = connection.prepareStatement(query1);
                    preparedStatement.setString(1,this.loginrequest.getUsername());
                    preparedStatement.setString(2, this.ip);
                    preparedStatement.setBoolean(3, false);

                    preparedStatement.executeUpdate();
                }
                return loginresult;
            case USERNOTEXIST:

            case PASSWORDNOTMATCHED:
                return new LoginResult(loginstatus);

            default:
                return new LoginResult(LoginStatus.ERROR);
        }
    }
}
