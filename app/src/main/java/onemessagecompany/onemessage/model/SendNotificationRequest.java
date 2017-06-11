package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 11/06/2017.
 */

public class SendNotificationRequest {


    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public OneMessageNotification getNotification() {
        return notification;
    }

    public void setNotification(OneMessageNotification notification) {
        this.notification = notification;
    }

    private OneMessageNotification notification;
}
