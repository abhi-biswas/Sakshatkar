package handlers;

import constants.BinaryStatus;
import constants.MessageType;
import mainclasses.Connector;
import requests.SendMessageRequest;
import results.ImageMessage;
import results.Message;
import results.TextMessage;
import results.VideoMessage;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;

// not tested yet

public class SendMessageHandler {
    private SendMessageRequest request;
    static private Integer textCount,videoCount,imageCount;
    static {
        textCount = 0;
        videoCount = 0;
        imageCount = 0;
    }

    public static int getTextCount() {
        return textCount;
    }

    public static int getVideoCount() {
        return videoCount;
    }

    public static int getImageCount() {
        return imageCount;
    }

    public SendMessageHandler(SendMessageRequest request) {
        this.request = request;
    }

    public BinaryStatus handle()
    {
        Message message = request.getMessage();
        Connection connection = Connector.getConnection();
        int messageid=0;
        if(message.getType()== MessageType.TEXT)
        {
            try {
                messageid = handleText(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return BinaryStatus.FAILURE;
            }
        }
        if(message.getType()==MessageType.IMAGE)
        {
            try {
                messageid = handleImage(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return BinaryStatus.FAILURE;
            }
        }
        if(message.getType()==MessageType.VIDEO)
        {
            try {
                messageid = handleVideo(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return BinaryStatus.FAILURE;
            }
        }
        String query = "Insert INTO `chat` (`type`, `messageid`, `sendername`, `recievername`, `sendtime`, `viewStatus`) VALUES (?, ?, ?, ?, ?, 0)";
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,message.getPrefix());
            statement.setInt(2,messageid);
            statement.setString(3,message.getSender());
            statement.setString(4,message.getReceiver());
            statement.setTimestamp(5,message.getTimestamp());
            int count = statement.executeUpdate();
            if(count!=1)
                throw new Exception("Error in insertion");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return BinaryStatus.FAILURE;
        }
        return BinaryStatus.SUCCESS;
    }
    public synchronized static int handleText(Message message) throws Exception
    {
        textCount++;
        try {
            String msgcontent = ((TextMessage)message).getTextData();
            String query = "insert into `textmessage`  VALUES (?, ?, ?, ?)";
            PreparedStatement statement = Connector.getConnection().prepareStatement(query);
            statement.setInt(1,textCount);
            statement.setBoolean(3, false);
            statement.setTimestamp(4,message.getTimestamp());
            statement.setString(2,msgcontent);
            int count = statement.executeUpdate();
            if(count!=1)
            {
                throw new Exception("Error in insertion");
            }
        }
        catch (Exception e)
        {
            textCount--;
            throw e;
        }
        return textCount;
    }

    public static synchronized int handleImage(Message message) throws Exception
    {
        imageCount++;
        try {
            String query = "insert INTO `imagemessage` VALUES (?, ?, ?, ?)";
            PreparedStatement statement = Connector.getConnection().prepareStatement(query);
            statement.setInt(1,imageCount);
            statement.setBoolean(3, false);
            statement.setTimestamp(4,message.getTimestamp());
            Blob blob = Connector.getConnection().createBlob();
            blob.setBytes(1,((ImageMessage)message).getImagedata());
            statement.setBlob(2,blob);
            int count = statement.executeUpdate();
            if(count!=1)
            {
                throw new Exception("Error in insertion");
            }
        }
        catch (Exception e)
        {
            imageCount--;
            throw e;
        }
        return  imageCount;
    }

    public static synchronized int handleVideo(Message message) throws Exception
    {
        videoCount++;
        try {
            String query = "insert INTO  `videomessage` VALUES (?, ?, ?, ?)";
            PreparedStatement statement = Connector.getConnection().prepareStatement(query);
            statement.setInt(1,videoCount);
            statement.setBoolean(3, false);
            statement.setTimestamp(4,message.getTimestamp());
            Blob blob = Connector.getConnection().createBlob();
            blob.setBytes(1,((VideoMessage)message).getVideodata());
            statement.setBlob(2,blob);
            int count = statement.executeUpdate();
            if(count!=1)
            {
                throw new Exception("Error in insertion");
            }
        }
        catch (Exception e)
        {
            videoCount--;
            throw e;
        }
        return videoCount;
    }


}
