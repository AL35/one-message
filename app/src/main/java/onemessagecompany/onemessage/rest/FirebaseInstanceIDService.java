package onemessagecompany.onemessage.rest;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 52Solution on 11/06/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        String refreshedToken=FirebaseInstanceId.getInstance().getToken();
    }
}
