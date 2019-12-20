package mainclasses;
import constants.FetchprofileStatus;
import handlers.Fetchprofilehandler;
import requests.Fetchprofilerequest;
import results.Fetchprofileresult;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        Fetchprofilerequest req = new Fetchprofilerequest("gaurav");
         Fetchprofilehandler handler= new Fetchprofilehandler(req);
        try{
            Fetchprofileresult fetchprofileresult =handler.handle();
            System.out.print("okkkk");
            if(fetchprofileresult.getLoginstatus()== FetchprofileStatus.SUCESS)
            {
                System.out.print("SUCESS");
            }
            else
            {
                System.out.println("Failure");
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
