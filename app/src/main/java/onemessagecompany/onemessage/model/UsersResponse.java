package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 52Solution on 4/06/2017.
 */

public class UsersResponse {
  public List<User> getResults() {
    return users;
  }

  public void setResults(List<User> results) {
    this.users = results;
  }

  @SerializedName("Users")
  private List<User> users;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @SerializedName("User")
  private User user;
}
