package DataTransferHandlers;
import mainclasses.Connector;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AudioTransfer implements  Runnable {
    private String sender,receiver;
    private int port;
    public AudioTransfer(String user1, String user2, int port)
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
                String senderhostname = null;
                if(resultSet.next())
                    senderhostname = resultSet.getString("ip");

                preparedStatement.setString(1,receiver );
                resultSet = preparedStatement.executeQuery();
                String receiverhostname = null;
                //code to get the ip address of sender from login table
                if(resultSet.next())
                    receiverhostname = resultSet.getString("ip");
                try{
                    DatagramSocket serverSocket = new DatagramSocket(port);
                    byte[] receiveData = new byte[1024];
                    byte[] sendData;
                    String address = ""+serverSocket.getRemoteSocketAddress();
                    if(address.equals(receiverhostname))
                    {
                        receiverhostname=senderhostname;
                        senderhostname = address;
                    }

                    InetAddress addressSender = InetAddress.getByName(senderhostname);
                    InetAddress addressReciever = InetAddress.getByName(receiverhostname);
                    while(true)
                    {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData,0, receiveData.length,addressSender,port);
                        serverSocket.receive(receivePacket);
                        receiveData = receivePacket.getData();
                        System.out.println("RECEIVED: " );
                        DatagramPacket sendPacket = new DatagramPacket(receiveData, 0,receiveData.length, addressReciever, port);
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
