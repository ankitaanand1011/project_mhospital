package sketch.m_hospital.com.m_hospital.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import sketch.m_hospital.com.m_hospital.Activities.Container;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 12/21/2017.
 */

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = context.getString(R.string.app_name);
        String notificationText = "Test notification";

        // Attempt to extract the "message" property from the payload: {"message":"Hello World!"}
        if (intent.getStringExtra("message") != null) {
            notificationText = intent.getStringExtra("message");
        }

        // Prepare a notification with vibration, sound and lights
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_status)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setLights(Color.RED, 1000, 1000)
                .setVibrate(new long[]{0, 400, 250, 400})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, Container.class), PendingIntent.FLAG_UPDATE_CURRENT));

        // Get an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        // Build the notification and display it
        notificationManager.notify(1, builder.build());
    }
}
