package handlers;

import constants.FetchSaltStatus;
import mainclasses.Connector;
import requests.FetchSaltRequest;
import results.FetchSaltResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FetchSaltHandler implements Handler {
    private static String checkQuery = "select count(*) from user where `username`=? ";
    private static String query = "select salt from user where `username`=?";
    private FetchSaltRequest req;

    public FetchSaltHandler(FetchSaltRequest req) {
        this.req = req;
    }

    public FetchSaltResult handle()
    {
        String salt = null;
        try {
            PreparedStatement statement1 = Connector.getConnection().prepareStatement(checkQuery);
            statement1.setString(1, req.getUsername());
            ResultSet rs = statement1.executeQuery();
            rs.next();
            if (rs.getInt(1) != 1)
                return new FetchSaltResult(FetchSaltStatus.USER_DOES_NOT_EXIST);
            PreparedStatement statement2 = Connector.getConnection().prepareStatement(query);
            statement2.setString(1,req.getUsername());
            rs = statement2.executeQuery();
            rs.next();
            salt = rs.getString(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new FetchSaltResult(FetchSaltStatus.ERROR);
        }
        return new FetchSaltResult(salt);
    }
}
