package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.ChangeUserPasswordRequest;
import onemessagecompany.onemessage.model.DeleteAccountRequest;
import onemessagecompany.onemessage.model.EditUserRequest;
import onemessagecompany.onemessage.model.EditUserResponse;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.model.UsersResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 52Solution on 4/06/2017.
 */

public interface UsersApi {

  @GET("/api/User")
  Call<UsersResponse> GetUsersList();


  @POST("/api/Account/EditUser")
  Call<EditUserResponse> EditUser(@Body EditUserRequest editUserRequest);

  @POST("/api/Account/ResetPassword")
  Call<Void> ChangePassword(@Body ChangeUserPasswordRequest changeUserPasswordRequest);



  @POST("/api/Account/DeleteAccount")
  Call<Void> DeleteAccount(@Body DeleteAccountRequest deleteAccountRequest);


  @GET("/api/User/UserById")
  Call<User> GetUserById(@Query("id") String id,@Query("type") String type);

}
