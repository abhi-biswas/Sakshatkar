package handlers;

import constants.BinaryStatus;
import mainclasses.Connector;
import requests.AcceptFriendRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AcceptFriendHandler {
    AcceptFriendRequest req;

    public AcceptFriendHandler(AcceptFriendRequest req) {
        this.req = req;
    }

    public BinaryStatus handle()
    {
        String query=
        "select count(*) from contacts where username in (?,?)";
        String query2 = "insert into contacts values (?,?)";
        String query3 = "delete from `friendrequests` where sendername = ? and recievername=?";
        try {
            PreparedStatement statement = Connector.getConnection().prepareStatement(query);
            statement.setString(1,req.getFriend());
            statement.setString(2,req.getUsername());
            ResultSet rs = statement.executeQuery();
            rs.next();
            if(rs.getInt(1)!=0)
                return BinaryStatus.SUCCESS;
            PreparedStatement statement1 = Connector.getConnection().prepareStatement(query2);
            statement1.setString(1,req.getFriend());
            statement1.setString(2,req.getUsername());
            int n = statement1.executeUpdate();
            if(n!=1)
                return BinaryStatus.FAILURE;
            PreparedStatement statement2 = Connector.getConnection().prepareStatement(query3);
            statement2.setString(2,req.getUsername());
            statement2.setString(1,req.getFriend());
            n  = statement2.executeUpdate();
            if(n!=1)
                return BinaryStatus.FAILURE;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return BinaryStatus.FAILURE;
        }
        return BinaryStatus.SUCCESS;
    }
}
