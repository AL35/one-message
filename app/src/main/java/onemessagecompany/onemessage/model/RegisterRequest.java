package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 3/06/2017.
 */

public class RegisterRequest {

  public String getEmail() {
    return Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getPassword() {
    return Password;
  }

  public void setPassword(String password) {
    Password = password;
  }

  public String getConfirmPassword() {
    return ConfirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    ConfirmPassword = confirmPassword;
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

  public boolean getIsEnabled() {
    return IsEnabled;
  }

  public void setIsEnabled(boolean isEnabled) {
    IsEnabled = isEnabled;
  }

  private String Email;
  private String UserName;
  private String Password;
  private String ConfirmPassword;
  private String FirstName;
  private String LastName;
  private boolean IsEnabled;

}
