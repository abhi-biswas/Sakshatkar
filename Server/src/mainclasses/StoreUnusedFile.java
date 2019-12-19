package mainclasses;

import java.sql.*;
import java.util.TimerTask;

// Status -
// True: File stored in local disk
//False : File stored in Database
public class StoreUnusedFile extends TimerTask {
    private long currenttime;
    private long maxdiff;
    private Connection connection;

    public StoreUnusedFile(){
        this.currenttime = System.currentTimeMillis();
        this.maxdiff = 86400000;        // time difference equivalent to one day
    }

    public void run() {

        connection = Connector.getConnection();

        storeunusedfile("videomessage");

        storeunusedfile("imagemessage");

    }

    public Boolean fileUnusedforlong(long lastusedtime){
        long diff = currenttime - lastusedtime;

        return diff > maxdiff;

    }

    public void storeunusedfile(String tablename){

        try {

            String query = "select * from " + tablename;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp(4);

                if(fileUnusedforlong(timestamp.getTime())){


                    Blob blob = resultSet.getBlob(2);
                    byte []ar = blob.getBytes(1, (int) blob.length());

                    FileInfo fileInfo = StoreHandler.store(ar);
                    int fileid = fileInfo.getFileid();
                    int messageid = resultSet.getInt(1);
                    String filepath = fileInfo.getFilepath();

                    String query1 = null;

                    if(tablename.equals("videomessage"))
                        query1 = "update videomessage set video = null, status = true where messageid = ?";
                    else
                        query1 = "update imagemessage set pic = null, status = true where messageid = ?";

                    updateDatabase(fileInfo, query1, fileid, messageid, filepath);
                }

            }


        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateDatabase(FileInfo fileInfo, String query1,int fileid,
                               int messageid, String filepath) throws SQLException{

        PreparedStatement preparedStatement;

        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.setInt(1, messageid);
        preparedStatement.executeUpdate();

        String query2 = "insert into storedin values (?, ?)";

        preparedStatement = connection.prepareStatement(query2);
        preparedStatement.setInt(1, fileid);
        preparedStatement.setInt(2, messageid);
        preparedStatement.executeUpdate();

        String query3 = "insert into files values (?, ?, CURRENT_TIMESTAMP)";

        preparedStatement = connection.prepareStatement(query3);
        preparedStatement.setInt(1, fileid);
        preparedStatement.setString(2, filepath);
        preparedStatement.executeUpdate();

    }

}
