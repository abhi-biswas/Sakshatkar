package handlers;

import constants.BinaryStatus;
import mainclasses.Connector;
import requests.FetchContactsRequest;
import results.ContactUserDetail;
import results.FetchContactsResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FetchContactsHandler implements Handler{
    FetchContactsRequest fetchContactsRequest;

    public FetchContactsHandler(FetchContactsRequest fetchContactsRequest){
        this.fetchContactsRequest = fetchContactsRequest;
    }

    public FetchContactsResult handle(){

        Connection connection = Connector.getConnection();
        ArrayList<ContactUserDetail> list = new ArrayList<>();

        String query = "select A.username, A.fname, A.lname, from user A, contacts B where ((B.username = ? and A.username" +
                " = B.contactname) or (B.contactname = ? and A.username = B.username))";



        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.fetchContactsRequest.getUsername());
            preparedStatement.setString(2, this.fetchContactsRequest.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();

            // get the list of contacts

            while(resultSet.next()){

                list.add(new ContactUserDetail(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }

            return new FetchContactsResult(list);

        }
        catch(SQLException e){
            e.printStackTrace();
            return new FetchContactsResult(BinaryStatus.FAILURE);
        }
    }
}
