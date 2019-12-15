package handlers;

import mainclasses.Connector;
import requests.FetchFriendReqListRequest;
import results.ShortUserDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FetchFriendReqListHandler {
    FetchFriendReqListRequest req;

    public FetchFriendReqListHandler(FetchFriendReqListRequest req) {
        this.req = req;
    }
    public ArrayList handle() throws SQLException {
        ArrayList<ShortUserDetail> list = new ArrayList<>();
        String query =
                "Select A.username,A.fname,A.lname,A.city  from user A, `friendrequests` B where B.recievername = ? and B.sendername = A.username" ;
        PreparedStatement statement = Connector.getConnection().prepareStatement(query);
        statement.setString(1,req.getUsername());
        ResultSet set = statement.executeQuery();
        while(set.next())
        {
            list.add(new ShortUserDetail(set.getString(1),set.getString(2),set.getString(3),set.getString(4)));
        }
        return list;
    }

}
