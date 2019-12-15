package requests;

import mainclasses.Connector;

import java.io.Serializable;
import java.sql.*;

public class Fetchprofilerequest implements Serializable {
    private String username;
    public Fetchprofilerequest(String username)
    {
        this.username=username;
    }

    public String getUsername() {
        return username;
    }









    }

