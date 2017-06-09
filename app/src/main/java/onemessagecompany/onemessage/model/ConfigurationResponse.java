package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 52Solution on 3/06/2017.
 */

public class ConfigurationResponse {

  public int getTime() {
    return Time;
  }

  public void setTime(int time) {
    Time = time;
  }

  @SerializedName("Time")
  private int Time;
}
