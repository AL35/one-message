package onemessagecompany.onemessage.rest;

import java.util.ArrayList;
import java.util.Map;

import onemessagecompany.onemessage.model.MessageResponse;
import onemessagecompany.onemessage.model.SendMessageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by 52Solution on 5/06/2017.
 */

public interface SendMessageApi {


  //  Call<SendMessageResponse> SendMessage(@QueryMap(encoded=false) Map<String, String> params);
  @GET("/api/Message/SendMessage")
  Call<SendMessageResponse> SendMessage(@Query("id") String id, @Query("msg") String msg);

  @GET("/api/Message/SendMessage")
  Call<SendMessageResponse> SendAdminMessage(@Query("msg") String msg);

  @POST("/api/Message/Messages")
  Call<MessageResponse> GetMessages();


  @POST("/api/Message/SeenMessages")
  Call<Void> SeenMessages(@Body ArrayList<Integer> ids);



  @DELETE("/api/Message/RemoveMessage")
  Call<Void> RemoveMessage(@Query("msgid") int msgid);

}