package handlers;

import Notification.AudioCallNotification;
import Notification.CallNotification;
import Notification.NotificationStatus;
import Notification.VideoCallNotification;
import constants.CallStatus;
import mainclasses.Connector;
import requests.CallRequest;
import requests.VideoCallRequest;
import requests.AudioCallRequest;
import results.CallResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CallHandler {

    private VideoCallRequest videoCallRequest;
    private AudioCallRequest audioCallRequest;
    private CallRequest callRequest;
    public CallHandler(VideoCallRequest videoCallRequest)
    {
            this.videoCallRequest=videoCallRequest;
            this.callRequest=videoCallRequest;
    }
    public CallHandler(AudioCallRequest audioCallRequest)
    {
        this.audioCallRequest=audioCallRequest;
        this.callRequest=audioCallRequest;
    }
    public CallResult handle() {
        try {

            String query = "select *from login where username=?";

            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
            preparedStatement.setString(1, this.callRequest.getCallee());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return new CallResult(this.callRequest.getCaller(), this.callRequest.getCallee(), CallStatus.CALLEEUNAVAILABLE);
            }
            if (rs.getInt("status") == 1) {
                return new CallResult(this.callRequest.getCaller(), this.callRequest.getCallee(), CallStatus.BUSY);
            } else {
                CallNotification callNotification;
                if (this.callRequest instanceof VideoCallRequest) {
                    callNotification = new VideoCallNotification(NotificationStatus.CALLREQUEST);
                } else {
                    callNotification = new AudioCallNotification(NotificationStatus.CALLREQUEST);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(callNotification);
                oos.flush();
                byte[] notification = bos.toByteArray();
                query = "select *from login where username=?";
                preparedStatement = Connector.getConnection().prepareStatement(query);
                preparedStatement.setString(1, this.callRequest.getCallee());
                rs = preparedStatement.executeQuery();


                String hostname = null;
                if (rs.next())
                    hostname = rs.getString("ip");
                InetAddress addressCaller = InetAddress.getByName(hostname);
                DatagramSocket ds = new DatagramSocket();
                DatagramPacket dpsend = new DatagramPacket(notification, notification.length, addressCaller, 3005);
                ds.send(dpsend);





                return new CallResult(this.callRequest.getCaller(), this.callRequest.getCallee(), CallStatus.BUSY);










            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /*false*/
        return new CallResult(this.callRequest.getCaller(), this.callRequest.getCallee(), CallStatus.BUSY);
    }


    }




