package mainclasses;

import constants.BinaryStatus;
import constants.MessageType;

import java.sql.*;
import java.util.TimerTask;

// inSecondary -
// True: File stored in local disk
//False : File stored in Database
public class StoreUnusedFile extends TimerTask {
    private long currenttime;
    private long maxdiff;
    private Connection connection;
    public static boolean storingIntoSecondary=false;
    public StoreUnusedFile(){
        this.currenttime = System.currentTimeMillis();
        this.maxdiff = 86400000;        // time difference equivalent to one day
    }

    public void run() {

        storingIntoSecondary = true;
        System.out.println("Storing messages into secondary memory");
        connection = Connector.getConnection();

        BinaryStatus binaryStatus = storeunusedfile("videomessage");

        binaryStatus = storeunusedfile("imagemessage");
        storingIntoSecondary = false;
        System.out.println("Storing done");
    }

    public Boolean fileUnusedforlong(long lastusedtime){
        long diff = currenttime - lastusedtime;
        return diff > maxdiff;

    }

    public BinaryStatus storeunusedfile(String tablename){

        try {

            String query = "select * from " + tablename+  " where inSecondary = false" ;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp(4);

                if(fileUnusedforlong(timestamp.getTime())){


                    Blob blob = resultSet.getBlob(2);
                    byte []ar = blob.getBytes(1, (int) blob.length());

                    FileInfo fileInfo = StoreHandler.store(ar);

                    if(fileInfo.getBinaryStatus() == BinaryStatus.FAILURE)
                        return BinaryStatus.FAILURE;


                    int fileid = fileInfo.getFileid();
                    int messageid = resultSet.getInt(1);
                    String filepath = fileInfo.getFilepath();

                    String query1 = null;

                    if(tablename.equals("videomessage"))
                    {
                        query1 = "update videomessage set video = null, inSecondary = true where messageid = ?";
                        updateDatabase(fileInfo, query1, fileid, messageid, filepath, MessageType.VIDEO);
                    }
                    else
                    {
                        query1 = "update imagemessage set pic = null, inSecondary = true where messageid = ?";
                        updateDatabase(fileInfo, query1, fileid, messageid, filepath, MessageType.IMAGE);
                    }
                }

            }

            return BinaryStatus.SUCCESS;

        }catch(Exception e){
            e.printStackTrace();
            return BinaryStatus.FAILURE;
        }
    }

    public BinaryStatus updateDatabase(FileInfo fileInfo, String query1,int fileid,
                               int messageid, String filepath, MessageType messageType){

        try {
            PreparedStatement preparedStatement;



            //insert data into storedin table
            String query2 = "insert into storedin values (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, fileid);
            preparedStatement.setString(2, messageType.toString());
            preparedStatement.setInt(3, messageid);
            preparedStatement.executeUpdate();
            //System.out.println("Query2 ran");

            //insert data into files table;
            String query3 = "insert into files values (?, ?, CURRENT_TIMESTAMP)";
            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.setInt(1, fileid);
            preparedStatement.setString(2, filepath);
            preparedStatement.executeUpdate();
            //System.out.println("Query3 ran");

            //Update Videomessage or Imagemeesage table;
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, messageid);
            preparedStatement.executeUpdate();
            //System.out.println("Query1 ran");

            return  BinaryStatus.SUCCESS;


        }catch (Exception e){
            e.printStackTrace();
            return BinaryStatus.FAILURE;
        }
    }

}
