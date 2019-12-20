package handlers;
import constants.ShowOnlineUserStatus;
import mainclasses.Connector;
import requests.ShowOnlineUserRequest;
import results.OnlineUserDetail;
import results.ShowOnlineUserResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ShowOnlineUserHandler {
    private Connection con= Connector.getConnection();
    private String username;
    private String ip;
    private ShowOnlineUserRequest showOnlineUserRequest;
    public ShowOnlineUserHandler(ShowOnlineUserRequest showOnlineUserRequest)
    {
        this.showOnlineUserRequest=showOnlineUserRequest;
    }
    public ShowOnlineUserResult handle() throws SQLException{

        String query ="select *from login";
        PreparedStatement preparedStatement=con.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<OnlineUserDetail> online=new ArrayList<>();
        ArrayList<String> username=new ArrayList<>();
     /*  if(!rs.next())
        {
            return new ShowOnlineUserResult(online,this.showOnlineUserRequest.getIp(),this.showOnlineUserRequest.getUsername(), ShowOnlineUserStatus.NOONEONLINE);

        }
*/
        while(rs.next())
        {

            username.add(rs.getString("username"));

        }
        con=Connector.getConnection();

        System.out.print(username.size());
        String uname,fname,lname;
        String query1 ="select *from user where username = ?";
        PreparedStatement preparedStatement1=con.prepareStatement(query1);
        for(int i=0;i<username.size();i++)
        {


            preparedStatement1.setString(1,username.get(i));
            ResultSet rs1 = preparedStatement1.executeQuery();
            rs1.next();
            uname=rs1.getString("username");
            fname=rs1.getString("fname");
            lname=rs1.getString("lname");
            online.add(new OnlineUserDetail(uname,fname,lname));
           // System.out.println(username.get(i));
        }
    return new ShowOnlineUserResult(online,this.showOnlineUserRequest.getIp(),this.showOnlineUserRequest.getUsername(), ShowOnlineUserStatus.SUCESS);

    }











    }





