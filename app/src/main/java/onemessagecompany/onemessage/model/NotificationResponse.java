package onemessagecompany.onemessage.model;

import java.util.List;

/**
 * Created by 52Solution on 8/06/2017.
 */

public class NotificationResponse {


  public List<Notification> getNotifications() {
    return Notifications;
  }

  public void setNotifications(List<Notification> notifications) {
    Notifications = notifications;
  }

  public List<Notification> Notifications ;

}
