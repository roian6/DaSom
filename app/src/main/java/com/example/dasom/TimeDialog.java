package com.example.dasom;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.dasom.receiver.AlarmReceiver;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class TimeDialog  {

    private Calendar calendar;
    private Context context;
    private AlarmManager alarmManager;
    private Intent intent1;
    private int hour,minute;
    private Spinner spinner;
    private TimePicker timePicker;
    private Button button;

    public TimeDialog(Context context) {
        this.context = context;
    }
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context,R.array.spinner_array,android.R.layout.simple_spinner_dropdown_item);
        final Dialog dlg = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.dialog_timeset);
        dlg.show();

        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        intent1 = new Intent(context, AlarmReceiver.class);

        spinner = dlg.findViewById(R.id.talk_cycle_changing_spinner);
        timePicker = dlg.findViewById(R.id.set_time_picker);
        button = dlg.findViewById(R.id.dialog_out);


        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SharedPreferences pref =context.getSharedPreferences("alarm_time", Context.MODE_PRIVATE);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("alarm_time_hour_int",hour);
                editor.putInt("alarm_time_minute_int",minute);
                editor.commit();
                dlg.dismiss();

            }
        });



        if (pref.getInt("alarm_time_hour_int", 0) != 0) {
            hour = pref.getInt("alarm_time_hour_int", 0);
            minute = pref.getInt("alarm_time_minute_int", 0);
        }

        //일단 현재시간 가져오는거 여기에 hour minute 갱신하여 넣어주기(TimePicker)
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);


        Log.e(" ",System.currentTimeMillis()+"");

        //spinner adapter 연결
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        AlarmPaused(context);
                        CreateAlarm(context,10,intent1);
                        break;
                    case 1:
                        AlarmPaused(context);
                        CreateAlarm(context,20,intent1);
                        break;
                    case 2:
                        AlarmPaused(context);
                        CreateAlarm(context,30,intent1);
                        break;
                    case 3:
                        AlarmPaused(context);
                        CreateAlarm(context,40,intent1);
                        break;
                    case 4:
                        AlarmPaused(context);
                        CreateAlarm(context,50,intent1);
                        break;
                    case 6:
                        AlarmPaused(context);
                        CreateAlarm(context,60,intent1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void CreateAlarm(Context context,int req, Intent intent){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,req,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("req",req);
        context.sendBroadcast(intent);
        setAlarm(pendingIntent,alarmManager);
    }

    public void AlarmPaused(Context context){
        AlarmPauseUtil(10,context);
        AlarmPauseUtil(20,context);
        AlarmPauseUtil(30,context);
        AlarmPauseUtil(40,context);
        AlarmPauseUtil(50,context);
        AlarmPauseUtil(60,context);

    }
    public void AlarmPauseUtil(int req,Context context){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,req,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmPause(pendingIntent);

    }
    public void AlarmPause(PendingIntent pendingIntent){
        alarmManager.cancel(pendingIntent);
    }

    public void setAlarm(PendingIntent pendingIntent, AlarmManager alarmManager){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()*20*60*1000,pendingIntent);
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()*20*60*1000,pendingIntent);
        }

    }

}
