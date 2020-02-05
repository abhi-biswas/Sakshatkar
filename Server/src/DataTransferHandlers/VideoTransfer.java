package DataTransferHandlers;
import mainclasses.Connector;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class VideoTransfer implements  Runnable {
    private String sender,receiver;
    private int port;
    public VideoTransfer(String user1, String user2, int port)
    {
        this.sender=user1;
        this.receiver=user2;
        this.port=port;
    }
    @Override
    public void run() {
        try{
            Connection connection = Connector.getConnection();
            String query = "select * from login where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            while (true) {
                //code to get the ip address of sender from login table

                preparedStatement.setString(1,sender );
                ResultSet resultSet = preparedStatement.executeQuery();
                String hostname = null;
                if(resultSet.next())
                    hostname = resultSet.getString("ip");
                InetAddress addressSender = InetAddress.getByName(hostname);

                preparedStatement.setString(1,receiver );
                resultSet = preparedStatement.executeQuery();
                hostname = null;
                //code to get the ip address of sender from login table
                if(resultSet.next())
                    hostname = resultSet.getString("ip");
                InetAddress addressReceiver = InetAddress.getByName(hostname);
                try{
                    DatagramSocket serverSocket = new DatagramSocket(port);
                    byte[] receiveData = new byte[1024];
                    byte[] sendData;

                    while(true)
                    {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData,0, receiveData.length,addressReceiver,port);
                        serverSocket.receive(receivePacket);
                        receiveData = receivePacket.getData();
                        System.out.println("RECEIVED: " );
                        DatagramPacket sendPacket = new DatagramPacket(receiveData, 0,receiveData.length, addressSender, port);
                        serverSocket.send(sendPacket);
                        System.out.println("SENt: " );
                    }
                }catch (Exception e){
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
