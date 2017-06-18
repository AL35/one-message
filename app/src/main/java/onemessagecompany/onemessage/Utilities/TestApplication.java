package onemessagecompany.onemessage.Utilities;

import android.app.Application;

/**
 * Created by lotfya on 6/18/2017.
 */

public class TestApplication extends Application {

    private static TestApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized TestApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
}
