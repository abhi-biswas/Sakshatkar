package handlers;

import constants.BinaryStatus;
import mainclasses.Connector;
import requests.DeleteGroupRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteGroupHandler {

    DeleteGroupRequest deleteGroupRequest;

    public DeleteGroupHandler(DeleteGroupRequest deleteGroupRequest){
        this.deleteGroupRequest = deleteGroupRequest;
    }

    public BinaryStatus handle(){
        Connection connection = Connector.getConnection();

        String query = "delete from groups where groupid = ?";

        String query1 = "delete from groupmembers where groupid = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, this.deleteGroupRequest.getGroupid());

            int stat = preparedStatement.executeUpdate();

            if(stat != 1){
                return BinaryStatus.FAILURE;
            }
            else
            {
                preparedStatement = connection.prepareStatement(query1);

                preparedStatement.setInt(1, this.deleteGroupRequest.getGroupid());

                preparedStatement.executeUpdate();

                return  BinaryStatus.SUCCESS;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return BinaryStatus.FAILURE;
        }
    }
}
