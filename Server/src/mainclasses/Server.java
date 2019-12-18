package mainclasses;

import constants.BinaryStatus;
import handlers.AcceptFriendHandler;
import handlers.FetchFriendReqListHandler;
import requests.AcceptFriendRequest;
import requests.FetchFriendReqListRequest;
import results.ShortUserDetail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        try {
                Connector.connecttoDatabase("sakshatkar");
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
        //Abhijeet's Tester
        AcceptFriendRequest req = new AcceptFriendRequest("hello2","hello1");
        AcceptFriendHandler handler = new AcceptFriendHandler(req);
        try{
            if(handler.handle()== BinaryStatus.SUCCESS)
                System.out.println("Success");
            else
                System.out.println("Failure");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //tester ends
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
