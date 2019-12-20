package handlers;

import constants.FetchMessagesStatus;
import mainclasses.Connector;
import requests.CountFetchMessagesRequest;
import requests.FetchMessagesRequest;
import requests.RangedFetchMessagesRequest;
import results.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FetchMessagesHandler {
    FetchMessagesRequest req;
    private static String rangeQuery = "select * from chat where recievername=? AND sendername = ? AND TIMESTAMPDIFF(SECOND, chat.sendtime,CURRENT_TIMESTAMP)<=?";
    private static String countQuery = "select * from chat where recievername=? AND sendername = ? order by sendtime desc limit ?";
    private static String checkQuery = "select count(*) from user where username=?";
    public FetchMessagesHandler(FetchMessagesRequest req) {
        this.req = req;
    }

    public FetchMessagesResult handle()
    {
        ArrayList<Message> list = new ArrayList<>();
        String query = null;
        int arg2=0;
        if(req instanceof RangedFetchMessagesRequest)
        {
            query = rangeQuery;
            arg2 = ((RangedFetchMessagesRequest) req).getRange();
        }
        else
        {
            query = countQuery;
            arg2 = ((CountFetchMessagesRequest) req).getCount();
        }
        try
        {
            PreparedStatement statement = Connector.getConnection().prepareStatement(checkQuery);
            statement.setString(1,req.getReceivername());
            ResultSet rs = statement.executeQuery();
            rs.next();
            if(rs.getInt(1)==0)
                return  new FetchMessagesResult(FetchMessagesStatus.USER_DOES_NOT_EXIST);
            statement = Connector.getConnection().prepareStatement(query);
            statement.setString(1,req.getReceivername());
            statement.setString(2,req.getSendername());
            statement.setInt(3,arg2);
            rs = statement.executeQuery();
            while(rs.next())
            {
                if(rs.getString("type").equals("TXT"))
                {
                    list.add (new TextMessage(rs.getString("sendername"),rs.getString("recievername"),rs.getTimestamp("sendtime"),getTextData(rs.getInt("messageid"))));
                }
                if (rs.getString("type").equals("IMG"))
                {
                    list.add (new ImageMessage(rs.getString("sendername"),rs.getString("recievername"),rs.getTimestamp("sendtime"),getByteData(rs.getInt("messageid"),"imagemessage","IMG")));
                }
                if(rs.getString("type").equals("VID"))
                {
                    list.add (new VideoMessage(rs.getString("sendername"),rs.getString("recievername"),rs.getTimestamp("sendtime"),getByteData(rs.getInt("messageid"),"videomessage","VID")));
                }
                String updatequery = "update chat set viewStatus=1 where type = ? and messageid = ?";
                PreparedStatement statement1 = Connector.getConnection().prepareStatement(updatequery);
                statement1.setString(1,rs.getString("type"));
                statement1.setInt(2,rs.getInt("messageid"));
                int count = statement1.executeUpdate();
                if(count!=1)
                    throw  new Exception("Error in update");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new FetchMessagesResult(FetchMessagesStatus.ERROR);
        }

        return new FetchMessagesResult(list);
    }
    private void updateTable(int messageid,String table) throws Exception
    {
        String query = "update "+table+ " set lastused = CURRENT_TIMESTAMP where messageid = ?";
        PreparedStatement statement = Connector.getConnection().prepareStatement(query);
        statement = Connector.getConnection().prepareStatement(query);
        statement.setInt(1,messageid);
        int count = statement.executeUpdate();
        if(count!=1)
            throw  new Exception("Error in update");
    }

    private String getTextData(int messageid) throws Exception
    {
        String query = "select msg from `textmessage` where messageid = ?";
        PreparedStatement statement = Connector.getConnection().prepareStatement(query);
        statement.setInt(1,messageid);
        ResultSet rs = statement.executeQuery();
        rs.next();
        String data = rs.getString(1);
        updateTable(messageid,"textmessage");
        return data;
    }
    private byte[] getByteData(int messageid , String table,String type) throws Exception
    {
        String query="select * from " + table + " where messageid = ?";
        PreparedStatement statement = Connector.getConnection().prepareStatement(query);
        statement.setInt(1,messageid);
        ResultSet rs = statement.executeQuery();
        byte data[] = null;
        rs.next();
        if(rs.getBoolean("inSecondary")==true)
        {
            data = retreiveFromFile(messageid,table,type);
        }
        else
        {
            Blob blob = rs.getBlob(2);
            data = blob.getBytes(1,(int)blob.length());
        }
        updateTable(messageid,table);
        return data;
    }
    //not tested
    private byte[] retreiveFromFile(int messageid,String table,String type) throws Exception
    {
        String query = "select filelocation,fileid from files natural join storedin natural join chat where chat.type=? and chat.messageid = ?";
        PreparedStatement statement = Connector.getConnection().prepareStatement(query);
        statement.setString(1,type);
        statement.setInt(2,messageid);
        ResultSet rs = statement.executeQuery();
        rs.next();
        String path = rs.getString(1);
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        String colname = null;
        if(type=="IMG")
            colname="pic";
        else
            colname = "video" ;
        String updateQuery = "update "+table +" set "+colname+ " = ? , inSecondary = false where messageid=?";
        statement = Connector.getConnection().prepareStatement(updateQuery);
        statement.setBlob(1,inputStream);
        statement.setInt(2,messageid);
        int count = statement.executeUpdate();
        if(count!=1)
            throw new Exception("Error in update");
        updateQuery = "delete from storedin where type = ?,messageid = ?";
        statement = Connector.getConnection().prepareStatement(updateQuery);
        statement.setString(1,type);
        statement.setInt(2,messageid);
        count = statement.executeUpdate();
        if(count!=1)
            throw new Exception("Error in deleting from storedin");
        updateQuery = " delete from files where fileid = ?";
        statement = Connector.getConnection().prepareStatement(updateQuery);
        statement.setInt(1,rs.getInt(2));
        count = statement.executeUpdate();
        if(count!=1)
            throw new Exception("Error in deleting from files");
        byte[] data = new byte[(int) file.length()];
        inputStream.read(data);
        return data;
    }
}
