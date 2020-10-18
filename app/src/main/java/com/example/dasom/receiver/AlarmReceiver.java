package com.example.dasom.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private int req;
    private int hourOfDay,minute;

    @Override
    public void onReceive(Context context, Intent intent) {
        req = intent.getExtras().getInt("req");
        if (req>0){
            Log.e("asd",req+"");
        }else{
            req = 10;
        }

        SharedPreferences pref = context.getSharedPreferences("alarm_time", Context.MODE_PRIVATE);
        hourOfDay = pref.getInt("alarm_time_hour_int",0);
        minute = pref.getInt("alarm_time_minute_int",0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(calendar.MINUTE,minute);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("alarm_time_hour_int", hourOfDay);
        editor.putInt("alarm_time_minute_int", minute);
        editor.commit();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent  = PendingIntent.getBroadcast(context,req,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        setAlarm(pendingIntent,alarmManager);

    }
    public void setAlarm(PendingIntent pendingIntent, AlarmManager alarmManager){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pendingIntent);
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pendingIntent);
        }

    }
}
