package onemessagecompany.onemessage.model;

import java.io.Serializable;

/**
 * Created by 52Solution on 4/06/2017.
 */

public class User  implements Serializable {

  public String Id ;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

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

  public String getPublicKey() {
    return PublicKey;
  }

  public void setPublicKey(String publicKey) {
    PublicKey = publicKey;
  }

  public String UserName ;
  public String FirstName ;
  public String LastName ;
  public String PublicKey ;

  public Boolean getIsEnabled() {
    return IsEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    IsEnabled = isEnabled;
  }

  public Boolean IsEnabled ;

}
