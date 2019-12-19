package handlers;

import constants.SignUpStatus;
import mainclasses.Connector;
import mainclasses.HashGenerator;
import mainclasses.SaltGenerator;
import requests.SignUpRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUpHandler {
    private SignUpRequest req;
    private static String query = "Insert INTO `user` (`username`, `fname`, `lname`, `password`, `salt`,  `addressline1`, `addressline2`, `city`, `pincode`, `ugroupid`) VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?) ";
    private static String checkQuery = "select count(*) from user where `username`=? ";

    public SignUpHandler(SignUpRequest req) {
        this.req = req;
    }

    public SignUpStatus handle()
    {
        try{
            PreparedStatement statement1 = Connector.getConnection().prepareStatement(checkQuery);
            statement1.setString(1,req.getUsername());
            ResultSet rs = statement1.executeQuery();
            rs.next();
            if(rs.getInt(1)!=0)
                return SignUpStatus.USERNAME_TAKEN;
            PreparedStatement statement2 = Connector.getConnection().prepareStatement(query);
            statement2.setString(1,req.getUsername());
            statement2.setString(2,req.getFname());
            statement2.setString(3,req.getLname());
            String salt = SaltGenerator.generateSalt();
            String password = HashGenerator.generateHash(salt+req.getPassword());
            statement2.setString(4,password);
            statement2.setString(5,salt );
            statement2.setString(6,req.getAddressline1());
            statement2.setString(7,req.getAddressline2());
            statement2.setString(8,req.getCity());
            statement2.setInt(9,req.getPincode());
            statement2.setInt(10,GroupCreationHandler.updategroupid());
            int count = statement2.executeUpdate();
            if(count!=1)
                throw  new Exception("Error in insertion");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return SignUpStatus.FAILURE;
        }
        return SignUpStatus.SUCCESS;
    }
}
