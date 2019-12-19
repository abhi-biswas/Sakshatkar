package handlers;

import constants.BinaryStatus;
import mainclasses.Connector;
import requests.GroupCreationRequest;
import results.GroupCreationResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupCreationHandler {
    private GroupCreationRequest groupCreationRequest;
    private static int groupid = 0;

    public GroupCreationHandler(GroupCreationRequest groupCreationRequest){
        this.groupCreationRequest = groupCreationRequest;
    }

    public GroupCreationResult handle(){
        Connection connection = Connector.getConnection();
        int gid = updategroupid();
        String query = "insert into groups values (?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gid);
            preparedStatement.setString(2, groupCreationRequest.getOwnername());
            preparedStatement.setString(3, groupCreationRequest.getGroupname());
            preparedStatement.setInt(4, groupCreationRequest.getGroupcapacity());

            int stat = preparedStatement.executeUpdate();


            if(stat != 1)
                return new GroupCreationResult(BinaryStatus.FAILURE);

            String query1 = "insert into partof values (?, ?)";

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, gid);
            preparedStatement.setString(2, groupCreationRequest.getOwnername());
            stat = preparedStatement.executeUpdate();



            if(stat != 1)
                return new GroupCreationResult(BinaryStatus.FAILURE);

            return new GroupCreationResult(groupid);


        }catch(SQLException e){
            e.printStackTrace();
            return new GroupCreationResult(BinaryStatus.FAILURE);
        }

    }

    public static synchronized int updategroupid(){
                groupid++;
                return groupid;
            }
}
