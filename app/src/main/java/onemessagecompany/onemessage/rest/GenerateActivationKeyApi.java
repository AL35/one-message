package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.GenerateKeyRequest;
import onemessagecompany.onemessage.model.GenerateKeyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by 52Solution on 3/06/2017.
 */

public interface GenerateActivationKeyApi {


  @GET("/api/ActivationKey/GenKey")
  Call<GenerateKeyResponse> GenerateActivationKey();
}
