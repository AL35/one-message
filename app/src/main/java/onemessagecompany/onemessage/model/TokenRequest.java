package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 30/05/2017.
 */

public class TokenRequest {

  public String getGrant_type() {
    return grant_type;
  }

  public void setGrant_type(String grant_type) {
    this.grant_type = grant_type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String grant_type;
  private String username;
  private String password;

}
