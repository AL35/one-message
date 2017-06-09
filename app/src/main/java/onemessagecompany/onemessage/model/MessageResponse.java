package onemessagecompany.onemessage.model;

import java.util.List;

/**
 * Created by 52Solution on 7/06/2017.
 */

public class MessageResponse {

  public List<Message> getMessages() {
    return Messages;
  }

  public void setMessages(List<Message> messages) {
    Messages = messages;
  }

  private List<Message> Messages ;

}
