package onemessagecompany.onemessage.rest;

import onemessagecompany.onemessage.model.OneMessageNotification;
import onemessagecompany.onemessage.model.SendMessageResponse;
import onemessagecompany.onemessage.model.SendNotificationRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 52Solution on 11/06/2017.
 */

public interface SendNotificationApi {

    @POST("/fcm/send")
    Call<Void> SendNotification(@Body SendNotificationRequest sendNotificationRequest);

}
