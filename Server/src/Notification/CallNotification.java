package Notification;

import javax.management.Notification;

public abstract class CallNotification {

    private  NotificationStatus notificationStatus;

    public CallNotification(NotificationStatus notificationStatus)
    {
        this.notificationStatus=notificationStatus;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }
    public abstract CallNotification CallNotificationType();
}
