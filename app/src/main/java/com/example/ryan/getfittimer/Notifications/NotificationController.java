package com.example.ryan.getfittimer.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.ryan.getfittimer.R;
import com.example.ryan.getfittimer.TimerActivity;

/**
 * Created by Ryan on 12/4/2017.
 */

public class NotificationController {
    NotificationCompat.Builder mBuilder;

    Context mContext;

    public NotificationController(Context context) {
        mContext = context;
        mBuilder = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("GetFitTimer notification")
                        .setContentText("Hello World!");


        Intent resultIntent = new Intent(mContext, TimerActivity.class);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, 0);

        mBuilder.setContentIntent(pendingIntent);


    }

    public void sendNotification(String Content) {
        mBuilder.setContentText(Content);

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }


}
