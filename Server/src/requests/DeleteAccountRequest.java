package requests;

import mainclasses.Connector;
import results.DeleteAccountResult;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAccountRequest implements Serializable {

    private String username,password;

    public DeleteAccountRequest(String username,String password)
    {
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
