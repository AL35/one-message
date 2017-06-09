package onemessagecompany.onemessage.model;

import java.util.List;

/**
 * Created by 52Solution on 7/06/2017.
 */

public class AdminRepliesResponse {

  public List<AdminReply> getResponse() {
    return Response;
  }

  public void setResponse(List<AdminReply> response) {
    Response = response;
  }

  private List<AdminReply> Response ;

}
