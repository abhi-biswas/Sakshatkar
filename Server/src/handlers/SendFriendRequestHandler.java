package handlers;
import constants.BinaryStatus;
import mainclasses.Connector;
import requests.SendFriendRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class SendFriendRequestHandler implements Handler {

   private SendFriendRequest req;
   Connection con ;

    public SendFriendRequestHandler(SendFriendRequest req) {
        this.req = req;
        con = Connector.getConnection();
    }
    public BinaryStatus handle()  {
        String query = "insert into `friendrequests` values (?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
            statement.setString(1,req.getSenderName());
            statement.setString(2,req.getReceiverName());
            statement.setTimestamp(3,timestamp);
            int n = statement.executeUpdate();
            if(n==1)
                return BinaryStatus.SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return BinaryStatus.FAILURE;
    }
}
