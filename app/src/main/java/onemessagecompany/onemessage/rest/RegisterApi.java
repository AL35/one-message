package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.RegisterRequest;
import onemessagecompany.onemessage.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by 52Solution on 3/06/2017.
 */

public interface RegisterApi {

  @POST("/api/Account/Register")
  Call<RegisterResponse> Register(@Body RegisterRequest registerRequest);

}
