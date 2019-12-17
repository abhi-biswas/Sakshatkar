package handlers;

import constants.AddGrpMemStatus;
import mainclasses.Connector;
import requests.AddGroupMemberRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class AddGroupMemberHandler {
    AddGroupMemberRequest addGroupMemberRequest;

    public AddGroupMemberHandler(AddGroupMemberRequest addGroupMemberRequest){
        this.addGroupMemberRequest = addGroupMemberRequest;
    }

    public AddGrpMemStatus handle(){
        Connection connection = Connector.getConnection();

        String query = "Select * from groups where groupid = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.addGroupMemberRequest.getGroupid());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                String query1 = "select * from user where username = ?";
                preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1,this.addGroupMemberRequest.getUsername());
                resultSet = preparedStatement.executeQuery();


                if(resultSet.next()) {

                    String query2 = "insert into groupmembers values (?, ?)";
                    preparedStatement = connection.prepareStatement(query2);

                    preparedStatement.setInt(1, this.addGroupMemberRequest.getGroupid());
                    preparedStatement.setString(2, this.addGroupMemberRequest.getUsername());

                    int status = preparedStatement.executeUpdate();

                    if (status != 1)
                        return AddGrpMemStatus.FAILURE;
                    return AddGrpMemStatus.SUCCESS;
                }
                else
                    return  AddGrpMemStatus.USERNOTEXIST;


            }
            else
                return AddGrpMemStatus.GROUPNOTEXIST;

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return AddGrpMemStatus.FAILURE;
        }
    }
}
