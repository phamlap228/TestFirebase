package com.lap0895.testfirebase;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class YourFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;
        Log.d("qaz", "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d("qaz", "Notification body: " + remoteMessage.getNotification().getBody());
            hanldeNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d("qaz", "Data payload: " + remoteMessage.getData().toString());
        }
    }

    private void hanldeNotification(String messege) {
        PushNotificationManager.getInstance().generateNotification(messege, Activity.class);
    }
}
