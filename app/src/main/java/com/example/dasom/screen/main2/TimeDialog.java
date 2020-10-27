package com.example.dasom.screen.main2;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;


import com.example.dasom.R;

import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class TimeDialog {

    private Context context;
    private Spinner spinner;
    private TimePicker timePicker;
    private Button button;
    private Intent intent1;
    private AlarmManager am;
    private int year,month,day,hour, minute,req;
    private long setTime;
    private int[] integers;
    private Bundle bundle;
    private Main2Fragment fragInfo = new Main2Fragment();
    public int cycle_time2;


    public TimeDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context, R.array.spinner_array, android.R.layout.simple_spinner_dropdown_item);
        final Dialog dlg = new Dialog(context, R.style.Theme_AppCompat_Light_Dialog);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.dialog_timeset);
        dlg.show();

        spinner = dlg.findViewById(R.id.talk_cycle_changing_spinner);
        timePicker = dlg.findViewById(R.id.set_time_picker);
        button = dlg.findViewById(R.id.dialog_out);

        intent1 = new Intent(context,AlarmReceiver.class);
        am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        integers = context.getResources().getIntArray(R.array.request_array);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmPaused(context);
                hour = timePicker.getHour();
                minute = timePicker.getMinute();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    year = Year.now().getValue();
                    month = YearMonth.now().getMonthValue();
                    day = MonthDay.now().getDayOfMonth();
                    LocalDateTime ldt = LocalDateTime.of(year,month,day,hour,minute);
                    ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Seoul"));
                    setTime = zdt.toInstant().toEpochMilli();
                }
                intent1.putExtra("req",req);
                testAlarm(setTime,context,req);
                dlg.dismiss();

            }
        });
        //spinner adapter 연결
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        req = integers[0];
                        break;
                    case 1:
                        req = integers[1];
                        break;
                    case 2:
                        req = integers[2];
                        break;
                    case 3:
                        req = integers[3];
                        break;
                    case 4:
                        req = integers[4];
                        break;
                    case 5:
                        req = integers[5];
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void testAlarm(long setTime,Context context,int req){
        Intent intent = new Intent(context,AlarmReceiver.class);
        intent.putExtra("req",req);
        PendingIntent sender = PendingIntent.getBroadcast(context, req, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setTime += 60000*req;
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,setTime,sender);
        }else{
            am.setExact(AlarmManager.RTC_WAKEUP,setTime,sender);
        }
    }




    public void AlarmPaused(Context context){

        AlarmPauseUtil(integers[0],context);
        AlarmPauseUtil(integers[1],context);
        AlarmPauseUtil(integers[2],context);
        AlarmPauseUtil(integers[3],context);
        AlarmPauseUtil(integers[4],context);
        AlarmPauseUtil(integers[5],context);
    }

    public void AlarmPauseUtil(int req,Context context){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,req,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmPause(pendingIntent);

    }
    public void AlarmPause(PendingIntent pendingIntent){
        am.cancel(pendingIntent);
    }
}
