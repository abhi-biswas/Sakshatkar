package Notification;

import com.sun.nio.sctp.Notification;

public class AudioCallNotification extends CallNotification{


    public AudioCallNotification(NotificationStatus notificationStatus){
        super(notificationStatus);

    };
    @Override
    public CallNotification CallNotificationType() {
        return new AudioCallNotification(null);
    }
}