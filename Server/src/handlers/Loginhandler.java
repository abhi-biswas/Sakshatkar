package handlers;

import mainclasses.Connector;
import constants.Loginstatus;
import requests.Loginrequest;
import results.Loginresult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class Loginhandler {
    private Loginrequest loginrequest;
    private Connection connection = null;
    private String ip = null;


    public Loginhandler(Loginrequest loginrequest,String ip){
        this.loginrequest = loginrequest;
        this.ip = ip;
    }
    public Loginresult handle() throws  Exception
    {
        return  checkCredentials();
    }
    public Loginresult checkCredentials() throws Exception{


        connection = Connector.getConnection();

        String query = "select * from user where username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.loginrequest.getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
                if(loginrequest.getPassword().equals(resultSet.getString("password")))
                    return getDetails(Loginstatus.SUCCESS);
                else
                    return getDetails(Loginstatus.PASSWORDNOTMATCHED);
        }
        else{
            return getDetails(Loginstatus.USERNOTEXIST);
        }
    }

    public Loginresult getDetails(Loginstatus loginstatus) throws Exception {

        switch (loginstatus) {

            case SUCCESS:
                String query = "select * from user where username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,this.loginrequest.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                Loginresult loginresult = null;


                if(resultSet.next()) {
                    loginresult = new Loginresult(loginstatus, this.loginrequest.getUsername(),
                            resultSet.getString("fname"), resultSet.getString("lname"));

                    query = "insert into `login` (username, ip, status) values(?, ?, ?)";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,this.loginrequest.getUsername());
                    preparedStatement.setString(2, this.ip);
                    preparedStatement.setBoolean(3, false);

                    preparedStatement.executeUpdate();
                }


                return loginresult;
            case USERNOTEXIST:

            case PASSWORDNOTMATCHED:
                return new Loginresult(loginstatus);

            default:
                return null;
        }
    }
}
