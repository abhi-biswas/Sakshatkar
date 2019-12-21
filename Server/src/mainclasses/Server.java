package mainclasses;

import handlers.FetchMessagesHandler;
import requests.CountFetchMessagesRequest;
import requests.FetchMessagesRequest;
import requests.RangedFetchMessagesRequest;
import results.FetchMessagesResult;
import results.Message;
import results.TextMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

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
            /*Timestamp timestamp = new Timestamp(12334+100000);
            byte arr[] = {1,2,3,4};
            TextMessage msg = new TextMessage("hello1", "hello2", timestamp, "Hello there, Babe");
            VideoMessage m2 = new VideoMessage("hello1","hello2",timestamp,arr);
            SendMessageRequest req = new SendMessageRequest(msg);
            SendMessageRequest req2 = new SendMessageRequest(m2);
            SendMessageHandler handler = new SendMessageHandler(req);
            SendMessageHandler h1 = new SendMessageHandler(req2);

        try {
            if (h1.handle() == BinaryStatus.SUCCESS)
                System.out.println("Success");
            else
                System.out.println("Failure");
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
       Timer timer =  new Timer();
        timer.schedule(new StoreUnusedFile(),0,5000);
        CountFetchMessagesRequest req1 = new CountFetchMessagesRequest("hello1","hello2");
        FetchMessagesHandler handler1 = new FetchMessagesHandler(req1);
        RangedFetchMessagesRequest req2 = new RangedFetchMessagesRequest("hello1","hello2",86400);
        FetchMessagesHandler handler2 = new FetchMessagesHandler(req2);
        try
        {
            FetchMessagesResult res1= handler1.handle();
            System.out.println(res1.getStatus());
            for(Message u : res1.getList())
            {
                System.out.println(u.getSender()+" "+u.getReceiver()+" "+u.getPrefix()+" "+u.getTimestamp());
            }
            FetchMessagesResult res2= handler2.handle();
            System.out.println(res2.getStatus());
            for(Message u : res2.getList())
            {
                System.out.println(u.getSender()+" "+u.getReceiver()+" "+u.getPrefix()+" "+u.getTimestamp()+" "+((TextMessage)u).getTextData());
            }
        }
        catch (Exception e)
        {

        }

        //tester ends
        while(true)
        {
            Socket socket=null;
            while(true)
            {
                try {
                    socket = serverSocket.accept();
                    System.out.println("A new Client " + socket.getInetAddress().getHostAddress() +" Accepted ");
                    Thread t = new Thread(new RequestHandler(socket));
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
