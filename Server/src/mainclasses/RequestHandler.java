package mainclasses;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import requests.*;
import handlers.*;
import results.*;


public class RequestHandler extends Thread
{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public RequestHandler(Socket socket)
    {
        try {

            this.socket = socket;
            //Reversing the order causes Deadlock and the project freezes
            //https://stackoverflow.com/questions/54095782/the-program-stops-when-the-objectinputstream-object-is-created
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());

        }
        catch (IOException e)
        {
            e.getMessage();
        }
    }

    @Override
    public void run()
    {
        System.out.println("Handling Client: "+socket.getRemoteSocketAddress());
        String client = ""+socket.getRemoteSocketAddress();
        while (true)
        {
            Object inReq = null;
            try{

                try{
                    inReq = ois.readObject();
                }
                catch (EOFException e)
                {
                    System.out.println(client + " Disconnected");
                    break;
                }

            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println(client + " Disconnected");
                break;
            }

        }
    }

}