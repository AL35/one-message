package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.ActivateRequest;
import onemessagecompany.onemessage.model.ActivateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 52Solution on 1/06/2017.
 */


  public interface ActivationApi {

    @POST("/api/ActivationKey/VerifyKey")
    Call<ActivateResponse> checkActivationAccess(@Body ActivateRequest activateRequest);
  }

