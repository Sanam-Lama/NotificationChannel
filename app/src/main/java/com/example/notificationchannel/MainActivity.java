package com.example.notificationchannel;

/* Notification channels enable us app developers to group our notifications into groups—channels—with the
 * user having the ability to modify notification settings for the entire channel at once.
 * In this example, a method is added or created to have the notification channel which is required for
 * Android versions 8.0 and above.
 *
 * 1. Small Icon
 * 2. App Name (Given by System)
 * 3. Timestamp
 * 4. Large Icon
 * 5. Content Title
 * 6. Content Text
 * */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final int NOTIFICATION_ID = 101;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotificationChannel();

                // creating an intent to send to the landingActivity
                Intent landingIntent = new Intent(MainActivity.this, LandingActivity.class);

                // these flag provide a proper navigation of activity when user opens the activity from notification
                landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // use of FLAG_ONE_SHOT means that this pendingIntent is used only for once
                PendingIntent landingPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

                // creating notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, NOTIFICATION_CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_sms_notification);
                builder.setContentTitle("Notification Channel");
                builder.setContentText("This is the description");
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                // in order to remove the notification from the notification bar when the user taps it, add this line
                builder.setAutoCancel(true);

                // add pendingIntent to the builder object
                builder.setContentIntent(landingPendingIntent);

                // issuing the notification
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
            }
        });
    }

    // method to create notification channel for Android version 8.0 and above
    public void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Personal Notification";
            String description = "Include all the personal notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
