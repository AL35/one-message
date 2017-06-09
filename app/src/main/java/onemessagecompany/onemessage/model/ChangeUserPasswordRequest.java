package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 4/06/2017.
 */

public class ChangeUserPasswordRequest {

  public String UserName;

  public String NewPassword ;

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getNewPassword() {
    return NewPassword;
  }

  public void setNewPassword(String newPassword) {
    NewPassword = newPassword;
  }

  public String getConfirmPassword() {
    return ConfirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    ConfirmPassword = confirmPassword;
  }

  public String ConfirmPassword;
}
