package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 4/06/2017.
 */

public class EditUserRequest {


  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

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

  public boolean isEnabled() {
    return IsEnabled;
  }

  public void setEnabled(boolean enabled) {
    IsEnabled = enabled;
  }

  private String UserName;
  private String FirstName;
  private String LastName;
  private boolean IsEnabled;
}
