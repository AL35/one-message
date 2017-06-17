package onemessagecompany.onemessage.rest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.Public.PublicMainActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;

/**
 * Created by 52Solution on 11/06/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (!sharedData.getAccessToken(getApplicationContext()).isEmpty() && sharedData.getAccessToken(getApplicationContext()) != " ") {
            if (remoteMessage.getData() != null) {
                Map<String, String> data = remoteMessage.getData();
                String title = data.get("title");
                String body = data.get("body");
                String isDeleted = data.get("deleted");
                if (isDeleted.equals("true") && sharedData.getRole(MyApplication.getContext()).equals("User"))
                    sendDeleteNotification();
                else
                    sendNotification(title, body);
            }
        }
    }

    private void sendDeleteNotification() {
        updateUserMainActivity(getApplicationContext(), "User");
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, PublicMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        if (sharedData.getRole(MyApplication.getContext()).equals("Administrator"))
            updateUserMainActivity(getApplicationContext(), "Administrator");
        else
            updateUserMainActivity(getApplicationContext(), "User");

    }

    static void updateUserMainActivity(Context context, String message) {

        Intent intent = new Intent("unique_name");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }
}
