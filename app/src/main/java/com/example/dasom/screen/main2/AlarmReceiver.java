package com.example.dasom.screen.main2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.dasom.R;
import com.example.dasom.screen.chat.ChatActivity;

import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private int year,month,day,hour,minute;
    private long setTime;
    private int req;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        req = intent.getIntExtra("req",0);
        if (req<0){
            req = 30;
        }
        Log.e("receiver",req+" ");

        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "asd")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getResources().getText(R.string.title))
                .setContentText(context.getResources().getText(R.string.description))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(context.getResources().getText(R.string.description)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Intent resultIntent = new Intent(context, ChatActivity.class);
        PendingIntent pending= PendingIntent.getActivity(context, 1002, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pending);
        builder.setAutoCancel(true);


        notificationManager.notify(10001, builder.build());
        year = Year.now().getValue();
        month = YearMonth.now().getMonthValue();
        day = MonthDay.now().getDayOfMonth();
        LocalDateTime asd = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(asd.getYear(), asd.getMonth(), asd.getDayOfMonth(), asd.getHour(), asd.getMinute());
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Seoul"));
        setTime = zdt.toInstant().toEpochMilli();
        testAlarm(context, setTime,req);

    }


    public void testAlarm(Context context,long setTime,int req){
        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, req, intent, 0);
        setTime += 60000*req; //10초 후 알람 이벤트 발생
        AlarmManager am = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,setTime,sender);
        }else{
            am.setExact(AlarmManager.RTC_WAKEUP,setTime,sender);
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.title);
            String description = context.getString(R.string.description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("asd", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
