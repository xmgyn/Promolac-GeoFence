package com.example.PromoLac.NotificationsTrying;

import java.io.Serializable;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MessagesNotifications {

    private String notificationId,notificationTitle,notificationDescription,notificationStatus,notificationTime,notificationAreaId;




    public MessagesNotifications() {
    }

    public MessagesNotifications(String notificationStatus ,String notificationDescription, String notificationTitle, String notificationTime, String notificationId, String notificationAreaId) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDescription = notificationDescription;
        this.notificationTime = notificationTime;
        this.notificationStatus = notificationStatus;
        this.notificationAreaId=notificationAreaId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }



    public String getNotificationAreaId() {
        return notificationAreaId;
    }

    public void setNotificationAreaId(String notificationAreaId) {
        this.notificationAreaId = notificationAreaId;
    }

    @Override
    public String toString() {
        return "MessagesNotifications{" +
                "notificationId='" + notificationId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationDescription='" + notificationDescription + '\'' +
                ", notificationStatus='" + notificationStatus + '\'' +
                ", notificationTime='" + notificationTime + '\'' +
                ", notificationAreaId='" + notificationAreaId + '\'' +
                '}';
    }
}
