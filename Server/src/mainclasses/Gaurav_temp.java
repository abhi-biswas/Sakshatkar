package mainclasses;
import constants.SearchUserStatus;
import constants.ShowOnlineUserStatus;
import constants.UpdateProfileStatus;
import handlers.SearchUserHandler;
import handlers.ShowOnlineUserHandler;
import requests.SearchUserRequest;
import requests.ShowOnlineUserRequest;
import results.SearchUserResult;
import results.ShortUserDetail;
import results.ShowOnlineUserResult;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Gaurav_temp {
    public static void main(String[] args) {
        try {
            Connector.connecttoDatabase("Sakshatkar");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ServerSocket serverSocket=null;
        try {
            serverSocket = new ServerSocket(6963);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        SearchUserRequest req = new SearchUserRequest("gaurav");
        SearchUserHandler handler= new SearchUserHandler(req);
        try{
            SearchUserResult searchUserResult =handler.handle();
            System.out.print("okkkk");
            if(searchUserResult.getSearchUserStatus()== SearchUserStatus.FOUND)
            {

                System.out.println("USER DETAILS");
                ShortUserDetail shortUserDetail=searchUserResult.getShortUserDetail();
                System.out.print(shortUserDetail.getUsername()+" "+shortUserDetail.getFname()+" "+shortUserDetail.getSname()+" "+shortUserDetail.getCity());
            }
            else
            {
                System.out.println("USERNOT FOUND");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        while(true)
        {
            Socket socket=null;
            while(true)
            {
                try {
                    socket = serverSocket.accept();
                    System.out.println("A new Client" + socket.getRemoteSocketAddress().toString() +" Accepted ");
                    Thread t = new Thread(new RequestHandler(socket));
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
