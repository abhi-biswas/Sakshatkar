package handlers;

import mainclasses.Connector;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioCallHandler implements Runnable{

    public void run(){
        try{
        Connection connection = Connector.getConnection();
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        TargetDataLine microphone;
        SourceDataLine speakers;
        microphone = AudioSystem.getTargetDataLine(format);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);

        String query = "select * from login where username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ByteArrayOutputStream out = new ByteArrayOutputStream();


        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
        speakers.start();
            int port = 5555;
            DatagramSocket socket = new DatagramSocket();
            DatagramSocket serverSocket = new DatagramSocket(3000);
            byte[] receiveData = new byte[1024];
            while (true) {


                //receive the username to whom the Audio message will be send

                DatagramPacket response1 = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(response1);
                String username = new String(receiveData, 0, response1.getLength());
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(response);


                //code to get the ip address from login table

                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                String hostname = null;


                //ip address of the receiver end;

                if(resultSet.next())
                    hostname = resultSet.getString("ip");
                InetAddress address = InetAddress.getByName(hostname);


                // send the audio message to the receiver

                out.write(response.getData(), 0, response.getData().length);
                DatagramPacket packet = new DatagramPacket(buffer, response.getLength(), address, port);
                socket.send(packet);
                System.out.println(hostname);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}