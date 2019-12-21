package Notification;

public class CallNotification {

    private  NotificationStatus notificationStatus;
    public CallNotification(NotificationStatus notificationStatus)
    {
        this.notificationStatus=notificationStatus;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }
}
