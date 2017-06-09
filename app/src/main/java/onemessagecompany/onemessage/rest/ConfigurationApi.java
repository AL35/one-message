package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.ConfigurationRequest;
import onemessagecompany.onemessage.model.ConfigurationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 52Solution on 3/06/2017.
 */

public interface ConfigurationApi {

  @POST("api/Configuration/SetConfiguration")
  Call<Void> SetConfiguration(@Body ConfigurationRequest configurationRequest);

  @GET("api/Configuration/GetConfiguration")
  Call<ConfigurationResponse> GetConfiguration();
}
