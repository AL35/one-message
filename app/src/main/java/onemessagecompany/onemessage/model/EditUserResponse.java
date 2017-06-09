package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 52Solution on 4/06/2017.
 */

public class EditUserResponse {

  public boolean isUserChanged() {
    return UserChanged;
  }

  public void setUserChanged(boolean userChanged) {
    UserChanged = userChanged;
  }

  @SerializedName("UserChanged")
  private boolean UserChanged;
}
