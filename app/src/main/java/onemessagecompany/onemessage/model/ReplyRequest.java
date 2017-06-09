package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 7/06/2017.
 */

public class ReplyRequest {

  public int getParentMessageId() {
    return ParentMessageId;
  }

  public void setParentMessageId(int parentMessageId) {
    ParentMessageId = parentMessageId;
  }

  public String getReplyMessage() {
    return ReplyMessage;
  }

  public void setReplyMessage(String replyMessage) {
    ReplyMessage = replyMessage;
  }

  private int ParentMessageId;

  private String ReplyMessage;

}
