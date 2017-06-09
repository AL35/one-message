package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 52Solution on 3/06/2017.
 */

public class GenerateKeyResponse {


  public String getKey() {
    return Key;
  }

  public void setKey(String key) {
    Key = key;
  }

  @SerializedName("Key")
  private String Key;
}
