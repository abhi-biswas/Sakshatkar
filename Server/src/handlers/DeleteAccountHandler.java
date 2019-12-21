package handlers;
import constants.DeleteAccountStatus;
import mainclasses.Connector;
import requests.DeleteAccountRequest;
import results.DeleteAccountResult;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DeleteAccountHandler implements Handler {
    private String username, password;
    private DeleteAccountRequest deleteAccountRequest;
    public DeleteAccountHandler(DeleteAccountRequest deleteAccountRequest) {
        this.deleteAccountRequest = deleteAccountRequest;
        password = this.deleteAccountRequest.getPassword();
        username = this.deleteAccountRequest.getUsername();
    }
    private Connection con = Connector.getConnection();
    public DeleteAccountResult handle()  {
        try {
            DeleteAccountStatus status = matchedPassword(username, password);
            if (status == DeleteAccountStatus.DELETEDSUCESSFULLY) {
                String query = "delete from user where username=?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, this.deleteAccountRequest.getUsername());
                preparedStatement.executeUpdate();
                query = "delete from login where username=?";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, this.deleteAccountRequest.getUsername());
                preparedStatement.executeUpdate();
                System.out.print("DELETED SUCESSFULLY");
            }
            return new DeleteAccountResult(username, status);
        }
        catch (SQLException e)
        {
            return new DeleteAccountResult(username,DeleteAccountStatus.ERROR);
        }
    }
    private DeleteAccountStatus matchedPassword(String username, String password) throws SQLException {
        String query1 = "select *from user where username=?";
        PreparedStatement stmt = con.prepareStatement(query1);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next())
        {
            return DeleteAccountStatus.USERNOTFOUND;
        }
        System.out.println(rs.getString("password"));
        System.out.print(rs.getString("password").equals(password));
        if (rs.getString("password").equals(password)) {
            return DeleteAccountStatus.DELETEDSUCESSFULLY;
        }
        return DeleteAccountStatus.DELETEDSUCESSFULLY.PASSWORDNOTMATCH;


    }

}