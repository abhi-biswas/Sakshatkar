package mainclasses;
import constants.ShowOnlineUserStatus;
import constants.UpdateProfileStatus;
import handlers.ShowOnlineUserHandler;
import requests.ShowOnlineUserRequest;
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
        ShowOnlineUserRequest req = new ShowOnlineUserRequest("gaurav","172.31.100.27");
        ShowOnlineUserHandler handler= new ShowOnlineUserHandler(req);
        try{
            ShowOnlineUserResult showOnlineUserResult =handler.handle();
            System.out.print("okkkk");
            if(showOnlineUserResult.getShowOnlineUserStatus()== ShowOnlineUserStatus.SUCESS)
            {

                System.out.println("LIST OF ONLINE USER");
                System.out.print(showOnlineUserResult.getOnline().size());
                for(int i=0;i<showOnlineUserResult.getOnline().size();i++)
                System.out.println(showOnlineUserResult.getOnline().get(i).getUsername()+showOnlineUserResult.getOnline().get(i).getFname());
            }
            else
            {
                System.out.println("Failure update");
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
