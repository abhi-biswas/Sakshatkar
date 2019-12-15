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


    public Loginhandler(Loginrequest loginrequest){
        this.loginrequest = loginrequest;
    }

    public Loginresult checkCredentials() throws Exception{


        connection = Connector.connecttoDatabase("Sakshatkar");

        String query = "select * from user where username = '"+ this.loginrequest.getUsername() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
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
                String query = "select * from user where username = '" + this.loginrequest.getUsername() + "'";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                Loginresult loginresult = null;
                if(resultSet.next())
                {
                    loginresult = new Loginresult(loginstatus, this.loginrequest.getUsername(),
                            resultSet.getString("fname"), resultSet.getString("lname"));
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
