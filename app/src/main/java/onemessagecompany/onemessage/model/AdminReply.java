package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 7/06/2017.
 */

public class AdminReply {

  public String FirstName;
  public String LastName;
  public String UserName;
  public String Body;

  public String getFirstName() {
    return FirstName;
  }

  public void setFirstName(String firstName) {
    FirstName = firstName;
  }

  public String getLastName() {
    return LastName;
  }

  public void setLastName(String lastName) {
    LastName = lastName;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getBody() {
    return Body;
  }

  public void setBody(String body) {
    Body = body;
  }

  public int getParentMessageId() {
    return ParentMessageId;
  }

  public void setParentMessageId(int parentMessageId) {
    ParentMessageId = parentMessageId;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public String getFromUserId() {
    return FromUserId;
  }

  public void setFromUserId(String fromUserId) {
    FromUserId = fromUserId;
  }

  public String getRV() {
    return RV;
  }

  public void setRV(String RV) {
    this.RV = RV;
  }

  public int ParentMessageId;
  public int ID;
  public String FromUserId;
  public String RV;

}
