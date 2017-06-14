package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.BoolResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by 52Solution on 14/06/2017.
 */

public interface CheckFirstTimeLoginApi {

    @PUT("api/User/UserChangedPassword")
    Call<Void> SetFirstTimePasswordChanged();

    @GET("api/User/IsFirstLogin")
    Call<BoolResponse> CheckFirstTimePasswordChanged();
}
