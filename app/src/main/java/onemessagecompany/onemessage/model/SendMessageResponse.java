package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 52Solution on 5/06/2017.
 */

public class SendMessageResponse {

  public String getResponse() {
    return Response;
  }

  public void setResponse(String response) {
    Response = response;
  }


  public String getError() {
    return Error;
  }

  public void setError(String error) {
    Error = error;
  }





  @SerializedName("Response")
  private  String Response;
  @SerializedName("Error")
  private  String Error;


}
