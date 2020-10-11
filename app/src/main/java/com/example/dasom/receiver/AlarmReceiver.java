package com.example.dasom.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private int req;

    @Override
    public void onReceive(Context context, Intent intent) {
        req = intent.getExtras().getInt("req");
        if (req>0){
            Log.e("asd",req+"");
        }else{
            req = 10;
        }


        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent  = PendingIntent.getBroadcast(context,req,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        setAlarm(pendingIntent,alarmManager);

    }
    public void setAlarm(PendingIntent pendingIntent, AlarmManager alarmManager){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pendingIntent);
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pendingIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+100000000,pendingIntent);
        }

    }
}
