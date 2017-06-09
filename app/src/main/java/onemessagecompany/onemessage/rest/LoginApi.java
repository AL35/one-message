package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.TokenRequest;
import onemessagecompany.onemessage.model.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 52Solution on 30/05/2017.
 */

public interface LoginApi {

  @POST("/token")
  @FormUrlEncoded
  Call<TokenResponse> getTokenAccess(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

  }
