package Notification;

import com.sun.nio.sctp.Notification;

public class VideoCallNotification extends CallNotification{


    public VideoCallNotification(NotificationStatus notificationStatus){
        super(notificationStatus);

    };
    @Override
    public CallNotification CallNotificationType() {
        return new VideoCallNotification(null);
    }
}