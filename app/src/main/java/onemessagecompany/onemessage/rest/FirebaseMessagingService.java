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

import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Admin.ForgetPasswordListActivity;
import onemessagecompany.onemessage.Admin.RepliesActivity;
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
                String type = data.get("type");

                if (sharedData.getRole(MyApplication.getContext()).equals("Administrator")) {
                    switch (type) {
                        case "forget":
                            sendAdminForgetPasswordNotification(title, body);
                            break;
                        case "reply":
                            sendAdminReplyNotification(title, body);
                            break;
                    }
                }
                if (sharedData.getRole(MyApplication.getContext()).equals("User")) {
                    switch (type) {
                        case "delete":
                            sendDeleteNotification();
                            break;
                        case "send":
                            sendNotification(title, body);
                            break;
                    }
                }
            }
        }
    }


    private void sendAdminForgetPasswordNotification(String title, String body) {
        Intent intent = new Intent(this, ForgetPasswordListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.om_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        updateForgetPasswordListActivity(MyApplication.getContext());


    }
    static void updateForgetPasswordListActivity(Context context) {

        Intent intent = new Intent("ForgetPassword");
        context.sendBroadcast(intent);
    }


    private void sendAdminReplyNotification(String title, String body) {
        Intent intent = new Intent(this, AdminMessageHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.om_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        updateAdminReplyActivity(MyApplication.getContext());


    }
    static void updateAdminReplyActivity(Context context) {

        Intent intent = new Intent("Reply");
        context.sendBroadcast(intent);
    }





    private void sendDeleteNotification() {
        updateUserMainActivity(getApplicationContext());
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, PublicMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.om_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        updateUserMainActivity(MyApplication.getContext());

    }

    static void updateUserMainActivity(Context context) {

        Intent intent = new Intent("Send");
        context.sendBroadcast(intent);
    }
}
