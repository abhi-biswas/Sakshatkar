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
        System.out.println("Handling Client: "+socket.getInetAddress().getHostAddress());
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
                Handler handler=null;

                if(inReq instanceof LoginRequest)
                    handler = new LoginHandler((LoginRequest) inReq,""+socket.getInetAddress().getHostAddress());

                if(inReq instanceof SignUpRequest)
                    handler = new SignUpHandler((SignUpRequest)inReq);

                if(inReq instanceof AcceptFriendRequest)
                    handler = new AcceptFriendHandler((AcceptFriendRequest) inReq);

                if(inReq instanceof FetchFriendReqListResult)
                    handler = new FetchFriendReqListHandler((FetchFriendReqListRequest) inReq);

                if(inReq instanceof FetchMessagesRequest)
                    handler = new FetchFriendReqListHandler((FetchFriendReqListRequest) inReq);

                if(inReq instanceof FetchSaltResult)
                    handler = new FetchSaltHandler((FetchSaltRequest) inReq);

                if(inReq instanceof SendFriendRequest)
                    handler = new SendFriendRequestHandler((SendFriendRequest) inReq);

                if(inReq instanceof SendMessageRequest)
                    handler = new SendMessageHandler((SendMessageRequest) inReq);

                if(inReq instanceof LogoutRequest)
                    handler = new LogoutHandler((LogoutRequest) inReq);

                if(inReq instanceof AddGroupMemberRequest)
                    handler = new AddGroupMemberHandler((AddGroupMemberRequest) inReq);

                if(inReq instanceof DeleteGroupRequest)
                    handler = new DeleteGroupHandler((DeleteGroupRequest) inReq);

                if(inReq instanceof FetchContactsRequest)
                    handler = new FetchContactsHandler((FetchContactsRequest) inReq);

                if(inReq instanceof GroupCreationRequest)
                    handler = new GroupCreationHandler((GroupCreationRequest) inReq);

                if(inReq instanceof CallConnectRequest)
                    handler = new CallConnectHandler((CallConnectRequest) inReq);


                //add your handler
                Object resp = handler.handle();
                oos.writeObject(resp);
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println(client + " Disconnected");
                break;
            }

        }
    }

}