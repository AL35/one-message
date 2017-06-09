package onemessagecompany.onemessage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 52Solution on 30/05/2017.
 */

public class TokenResponse {
  public String getAccess_token() {
    return access_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public int getExpires_in() {
    return expires_in;
  }

  public String getUserName() {
    return userName;
  }

  @SerializedName("access_token")
  private String access_token;

  @SerializedName("token_type")
  private String token_type;

  @SerializedName("expires_in")
  private int expires_in;

  @SerializedName("userName")
  private String userName;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @SerializedName("role")
  private String role;
}
