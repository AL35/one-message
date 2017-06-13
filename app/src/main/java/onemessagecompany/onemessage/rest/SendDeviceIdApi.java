package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.DeviceIdRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by 52Solution on 14/06/2017.
 */

public interface SendDeviceIdApi {


    @PUT("/api/User/DeviceId")
    Call<Void> SendDeviceId(@Body DeviceIdRequest deviceIdRequest);
}
