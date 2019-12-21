package mainclasses;
import constants.DeleteAccountStatus;
import handlers.DeleteAccountHandler;
import requests.DeleteAccountRequest;
import results.DeleteAccountResult;
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
        DeleteAccountRequest req = new DeleteAccountRequest("gaurav","Gaurav@12");
        DeleteAccountHandler handler= new DeleteAccountHandler(req);
        try{
            DeleteAccountResult deleteAccountResult = handler.handle();
            if(deleteAccountResult.getDeleteAccountStatus()== DeleteAccountStatus.DELETEDSUCESSFULLY)
            {

                System.out.println("USER DELETED");

                System.out.print(deleteAccountResult.getUsername());
            }
            else if(deleteAccountResult.getDeleteAccountStatus()== DeleteAccountStatus.USERNOTFOUND)
            {
                System.out.println("USER NOT FOUND");
            }
            else if(deleteAccountResult.getDeleteAccountStatus()== DeleteAccountStatus.PASSWORDNOTMATCH)
            {
                System.out.print("Password not matched");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        while (true)
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
