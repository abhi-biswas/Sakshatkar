package mainclasses;

import handlers.AudioCallHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

public class Server {

    public static void main(String[] args) throws Exception{

        Connector.connecttoDatabase("Sakshatkar");

        ServerSocket serverSocket=null;
        serverSocket = new ServerSocket(6963);

        //Timer class to store unused files
        Timer timer =  new Timer();
        timer.schedule(new StoreUnusedFile(),0,50);

        //AudioCallHandlerThreadStarted
        Thread thread = new Thread(new AudioCallHandler());
        thread.start();

        // Socket thread to handle different requests
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
