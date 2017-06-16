package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.ForgetPasswordRequest;
import onemessagecompany.onemessage.model.ForgetPasswordResponse;
import onemessagecompany.onemessage.model.NotificationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 52Solution on 8/06/2017.
 */

public interface ForgetPasswordApi {

  @GET("/api/Notification/GetNotifications")
  Call<NotificationResponse> GetNotifications();

  @POST("/api/User/UserForgetPassword")
  Call<ForgetPasswordResponse> ForgetPassword(@Body ForgetPasswordRequest forgetPasswordRequest);



}
