package handlers;

import constants.CallConnectStatus;
import constants.BinaryStatus;
import mainclasses.Connector;
import requests.CallConnectRequest;
import results.CallConnectResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CallConnectHandler implements Handler {
    private CallConnectRequest callConnectRequest;
    private Connection connection;

    public CallConnectHandler(CallConnectRequest callConnectRequest){
        this.callConnectRequest = callConnectRequest;
    }


    @Override
    public Object handle() {

        connection = Connector.getConnection();

        String query = "select * from login where username = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.callConnectRequest.getCalleename());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                if(resultSet.getBoolean("busyOnCall")){
                    return new CallConnectResult(CallConnectStatus.CALLEEBUSY);
                }
                else
                {
                    CallConnectResult callConnectResult = sendAudioCallConnectRequest(resultSet.getString("ip"));

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

    public CallConnectResult sendAudioCallConnectRequest(String ip){

        CallConnectResult callConnectResult = null;
        try {
            Socket socket = new Socket(ip, 2770);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(this.callConnectRequest);
            objectOutputStream.flush();
            objectOutputStream.close();

            ObjectInputStream objectInputStream = new ObjectInputStream((socket.getInputStream()));

            callConnectResult = (CallConnectResult) objectInputStream.readObject();

            objectInputStream.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new CallConnectResult(CallConnectStatus.CONNECTFAILURE);
        }
        return callConnectResult;
    }

    public BinaryStatus updateDatabase(){

        String query = "update login set busyOnCall = true where username = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);


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
