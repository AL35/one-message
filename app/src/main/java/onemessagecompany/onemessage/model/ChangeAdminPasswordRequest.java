package onemessagecompany.onemessage.model;

/**
 * Created by lotfya on 6/13/2017.
 */

public class ChangeAdminPasswordRequest {

    public String OldPassword;

    public String NewPassword;

    public String ConfirmPassword;

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getNewPassword() { return NewPassword; }

    public void setNewPassword(String newPassword) { NewPassword = newPassword; }

    public String getConfirmPassword() { return ConfirmPassword; }

    public void setConfirmPassword(String confirmPassword) { ConfirmPassword = confirmPassword; }

}
