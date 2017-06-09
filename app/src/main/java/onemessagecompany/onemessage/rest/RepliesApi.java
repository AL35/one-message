package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.AdminRepliesResponse;
import onemessagecompany.onemessage.model.MessageResponse;
import onemessagecompany.onemessage.model.ReplyRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 52Solution on 7/06/2017.
 */

public interface RepliesApi {

  @GET("/api/Reply/Reply")
  Call<AdminRepliesResponse> GetReplies(@Query("msgid") String msgid);


  @POST("/api/Reply/Reply")
  Call<Void> sendReply(@Body ReplyRequest replyRequest);
}
