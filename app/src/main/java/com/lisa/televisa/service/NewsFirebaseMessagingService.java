package com.lisa.televisa.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lisa.televisa.MainActivity;
import com.lisa.televisa.config.Config;
import com.lisa.televisa.persistence.NewsData;
import com.lisa.televisa.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by hever on 11/11/16.
 */

public class NewsFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = NewsFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    public NewsData newsData;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

       // if (remoteMessage == null)
       //     return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());

            handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData().get("postID"));
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        Log.d(TAG, "BACKGROUND");

    }

    private void handleNotification(String message, String postID) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);

            pushNotification.putExtra("message", message);
            pushNotification.putExtra("postID", postID);

            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        }else{
            // If the app is in background, firebase itself handles the notification
           Log.d(TAG, "Viene de background");

        }
    }

    private void handleDataMessage(JSONObject json) {

        Log.e(TAG, "push json: " + json.toString());

        newsData =  new NewsData(getApplicationContext());

        try {

            JSONObject data = json.getJSONObject("data");

            Log.d("JSON PUSH", data.toString());

            newsData.setPostID(json.getString("postID"));

            String title            = data.getString("title");
            String message          = data.getString("message");
            boolean isBackground    = data.getBoolean("is_background");
            String imageUrl         = data.getString("image");
            String timestamp        = data.getString("timestamp");
            String postID           = data.getString("postID");
            JSONObject payload      = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);

                pushNotification.putExtra("message", message);
                pushNotification.putExtra("postID", postID);

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                resultIntent.putExtra("postID", postID);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, postID, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, postID , resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, String postID, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, postID, intent);
        Log.d(TAG, "text only");
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, String postID, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, postID, intent, imageUrl);
        Log.d(TAG, "text and image");
    }
}