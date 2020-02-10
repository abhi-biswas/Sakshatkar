package DataTransferHandlers;
import mainclasses.Connector;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author kunal Anand
 */
public class DataTransfer implements  Runnable {
    private int port;
    public DataTransfer(int port)
    {
        this.port=port;
    }
    @Override
    public void run() {

            try{
                DatagramSocket serverSocket = new DatagramSocket(port);
                byte[] receiveData = new byte[1024];
                byte[] sendData;
                String query = "select * from call where ip1 = ? or ip2 = ?";
                Connection connection = Connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = null;

                while(true)
                {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData,0, receiveData.length);
                    serverSocket.receive(receivePacket);
                    receiveData = receivePacket.getData();
                    System.out.println("RECEIVED: " );
                    String address = ""+receivePacket.getAddress();
                    preparedStatement.setString(1,address);
                    preparedStatement.setString(2,address);
                    resultSet = preparedStatement.executeQuery();
                    String sender = resultSet.getString(1);
                    String receiver = resultSet.getString(2);
                    if(address.equals(receiver))
                    {
                        receiver = sender;
                        sender = address;
                    }
                    InetAddress addressReciever = InetAddress.getByName(receiver);
                    DatagramPacket sendPacket = new DatagramPacket(receiveData, 0,receiveData.length, addressReciever, port);
                    serverSocket.send(sendPacket);
                    System.out.println("SENT: " );
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


    }
}
