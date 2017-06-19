package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.VersionNumberResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 52Solution on 19/06/2017.
 */

public interface VersionNumberApi {


    @GET("/api/Configuration/ServerVersion")
    Call<VersionNumberResponse> GetVersionNumber();

}
