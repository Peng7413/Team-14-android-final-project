package com.example.grandfinal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

/**
 *
 * @ClassName: AlarmReceiver
 * @Description: 鬧鈴時間到了會進入這個廣播，這個時候可以做一些該做的業務。
 * @author zk
 */
public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Toast.makeText(context, "鬧鈴響了", Toast.LENGTH_LONG).show();
//			NotificationManager nm=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//			Notification notification=new Notification(R.drawable.ic_launcher,"時間到了",1000);
//			notification.flags=Notification.DEFAULT_ALL;
////			notification.defaults=Notification.DEFAULT_ALL;
//			nm.notify(0, notification);
        deliverNotification(context);
    }
    private void deliverNotification(Context context) {
        // Create the content intent for the notification, which launches
        // this activity
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("time notification")
                .setContentText("is time up")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
