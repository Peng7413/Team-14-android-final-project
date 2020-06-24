package com.example.grandfinal;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.NOTIFICATION_SERVICE;

public class fragment3 extends Fragment {
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;
    final String TAG = MainActivity.class.getSimpleName();
    TimePicker mTimePicker;
    Button mButton1;
    Button mButton2;
    Button mButtonCancel;
    int mHour = 1;
    int mMinute = 1;
    int mSecond =1;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.frag3, container, false);
        // 獲取當前時間
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        mSecond=calendar.get(Calendar.SECOND);
        mNotificationManager = (NotificationManager)
                getActivity().getSystemService(NOTIFICATION_SERVICE);
        mTimePicker = (TimePicker)rootView.findViewById(R.id.timePicker);
        mTimePicker.setCurrentHour(mHour);
        mTimePicker.setCurrentMinute(mMinute);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });
        mButton1 = (Button)rootView.findViewById(R.id.normal_button);
        mButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(getActivity().getApplication(), NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                long firstTime = SystemClock.elapsedRealtime();	// 開機之後到現在的執行時間(包括睡眠時間)
                long systemTime = System.currentTimeMillis();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 這裡時區需要設定一下，不然會有8個小時的時間差
                calendar.set(Calendar.MINUTE, mMinute);
                calendar.set(Calendar.HOUR_OF_DAY, mHour);
                calendar.set(Calendar.SECOND, 0);
                //calendar.set(Calendar.MILLISECOND, 0);

                // 選擇的每天定時時間
                long selectTime = calendar.getTimeInMillis();

                // 如果當前時間大於設定的時間，那麼就從第二天的設定時間開始
                if(systemTime > selectTime) {
                    Toast.makeText(getActivity().getApplication(), "設定的時間小於當前時間", Toast.LENGTH_SHORT).show();
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    selectTime = calendar.getTimeInMillis();
                }
                // 計算現在時間到設定時間的時間差
                long time = selectTime - systemTime;
                firstTime += time;
                AlarmManager manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, sender);
                Toast.makeText(getActivity().getApplication(), "設定簡單鬧鈴成功!", Toast.LENGTH_LONG).show();
            }
        });

        mButton2 = (Button)rootView.findViewById(R.id.repeating_button);
        mButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplication(), AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(getActivity().getApplication(), NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                long firstTime = SystemClock.elapsedRealtime();	// 開機之後到現在的執行時間(包括睡眠時間)
                long systemTime = System.currentTimeMillis();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 這裡時區需要設定一下，不然會有8個小時的時間差
                calendar.set(Calendar.MINUTE, mMinute);
                calendar.set(Calendar.HOUR_OF_DAY, mHour);
                calendar.set(Calendar.SECOND, 0);
                //calendar.set(Calendar.MILLISECOND, 0);

                // 選擇的每天定時時間
                long selectTime = calendar.getTimeInMillis();

                // 如果當前時間大於設定的時間，那麼就從第二天的設定時間開始
                if(systemTime > selectTime) {
                    Toast.makeText(getActivity().getApplication(), "設定的時間小於當前時間", Toast.LENGTH_SHORT).show();
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    selectTime = calendar.getTimeInMillis();
                }
                // 計算現在時間到設定時間的時間差
                long time = selectTime - systemTime;
                firstTime += time;
                // 進行鬧鈴註冊
                AlarmManager manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime, AlarmManager.INTERVAL_DAY, sender);
                Toast.makeText(getActivity().getApplication(), "設定重複鬧鈴成功! ", Toast.LENGTH_LONG).show();
            }
        });

        mButtonCancel = (Button)rootView.findViewById(R.id.cancel_button);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplication(), AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(getActivity().getApplication(),
                        0, intent, 0);

                // 取消鬧鈴
                AlarmManager am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                am.cancel(sender);
            }
        });
        createNotificationChannel();
        return rootView;
    }
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to " +
                    "stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
