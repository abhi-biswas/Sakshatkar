package mainclasses;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
    static Connection con;
    public static  Connection getConnection()
    {
        return con;
    }
    public static void connecttoDatabase(String Databasename) throws Exception{

        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/" + Databasename;
        connection = DriverManager.getConnection(url, "root", "");
        con = connection;
    }
}
