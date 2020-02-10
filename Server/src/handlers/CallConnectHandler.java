package handlers;

/**
 * @author : Kunal Anand
 */

import constants.CallConnectStatus;
import constants.BinaryStatus;
import mainclasses.Connector;
import requests.CallConnectRequest;
import results.CallConnectResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CallConnectHandler implements Handler {
    private CallConnectRequest callConnectRequest;
    private Connection connection;
    private String caller,callee;
    private String callerip, calleeip;

    public CallConnectHandler(CallConnectRequest callConnectRequest){
        this.callConnectRequest = callConnectRequest;
    }


    @Override
    public Object handle() {

        connection = Connector.getConnection();
        caller = callConnectRequest.getCallername();
        callee = callConnectRequest.getCalleename();

        String query = "select * from login where username = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.callConnectRequest.getCallername());

            ResultSet resultSet = preparedStatement.executeQuery();

            callerip = resultSet.getString("ip");

            preparedStatement.setString(1, this.callConnectRequest.getCalleename());

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                if(resultSet.getBoolean("busyOnCall")){
                    return new CallConnectResult(CallConnectStatus.CALLEEBUSY);
                }
                else
                {
                    CallConnectResult callConnectResult = new CallConnectResult(callConnectRequest.getCallType());
                    calleeip = resultSet.getString("ip");
                    if(callConnectResult.getCallConnectStatus() == CallConnectStatus.CONNECTSUCCESSFUL){

                        BinaryStatus binaryStatus = updateDatabase();
                        if(binaryStatus == BinaryStatus.FAILURE)
                            return new CallConnectResult(CallConnectStatus.CONNECTFAILURE);

                    }

                    return callConnectResult;
                }
            }
            else
                return new CallConnectResult(CallConnectStatus.CALLEEOFFLINE);
        }catch (SQLException e){
            e.printStackTrace();
            return new CallConnectResult(CallConnectStatus.CONNECTFAILURE);
        }

    }

    public BinaryStatus updateDatabase(){

        String query = "update login set busyOnCall = true where username = ?";
        String query1 = "insert into call values(?, ?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,callerip);
            preparedStatement.setString(2,calleeip);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.callConnectRequest.getCalleename());
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, this.callConnectRequest.getCallername());
            preparedStatement.executeUpdate();

            return BinaryStatus.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return  BinaryStatus.FAILURE;
        }
    }
}
