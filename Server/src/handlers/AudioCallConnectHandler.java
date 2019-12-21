package handlers;

import constants.AudioCallConnectStatus;
import constants.BinaryStatus;
import mainclasses.Connector;
import requests.AudioCallConnectRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AudioCallConnectHandler implements Handler {
    private AudioCallConnectRequest audioCallConnectRequest;
    private Connection connection;

    public AudioCallConnectHandler(AudioCallConnectRequest audioCallConnectRequest){
        this.audioCallConnectRequest = audioCallConnectRequest;
    }


    @Override
    public Object handle() {

        connection = Connector.getConnection();

        String query = "select * from login where username = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.audioCallConnectRequest.getCalleename());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                if(resultSet.getBoolean("busyOnCall")){
                    return AudioCallConnectStatus.CALLEEBUSY;
                }
                else
                {
                    AudioCallConnectStatus audioCallConnectStatus = sendAudioCallConnectRequest(resultSet.getString("ip"));

                    if(audioCallConnectStatus == AudioCallConnectStatus.CONNECTSUCCESSFUL){

                        BinaryStatus binaryStatus = updateDatabase();
                        if(binaryStatus == BinaryStatus.FAILURE)
                            return AudioCallConnectStatus.CONNECTFAILURE;

                    }

                    return audioCallConnectStatus;
                }
            }
            else
                return AudioCallConnectStatus.CALLEEOFFLINE;
        }catch (SQLException e){
            e.printStackTrace();
            return AudioCallConnectStatus.CONNECTFAILURE;
        }

    }

    public AudioCallConnectStatus sendAudioCallConnectRequest(String ip){

        AudioCallConnectStatus audioCallConnectStatus = null;
        try {
            Socket socket = new Socket(ip, 2770);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(this.audioCallConnectRequest);
            objectOutputStream.flush();
            objectOutputStream.close();

            ObjectInputStream objectInputStream = new ObjectInputStream((socket.getInputStream()));

            audioCallConnectStatus = (AudioCallConnectStatus)objectInputStream.readObject();

            objectInputStream.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  audioCallConnectStatus;
    }

    public BinaryStatus updateDatabase(){

        String query = "update login set busyOnCall = true where username = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1, this.audioCallConnectRequest.getCalleename());
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, this.audioCallConnectRequest.getCallername());
            preparedStatement.executeUpdate();

            return BinaryStatus.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return  BinaryStatus.FAILURE;
        }
    }
}
