package com.example.dasom.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dasom.R;
import com.example.dasom.databinding.FragmentMain2Binding;
import com.example.dasom.receiver.AlarmReceiver;

import org.jetbrains.annotations.NotNull;

public class Main2Fragment extends Fragment{

    public static Main2Fragment newInstance() {
        return new Main2Fragment();
    }

    private Context mContext;
    private FragmentMain2Binding binding;
    private AlarmManager alarmManager;
    private Intent intent1;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main2, container, false);

        alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext,R.array.spinner_array,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intent1 = new Intent(mContext,AlarmReceiver.class);

        binding.talkCycleChangingSpinner.setAdapter(arrayAdapter);

        binding.talkCycleChangingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        AlarmPaused(mContext);
                        CreateAlarm(mContext,10,intent1);
                        break;
                    case 1:
                        AlarmPaused(mContext);
                        CreateAlarm(mContext,20,intent1);
                        break;
                    case 2:
                        AlarmPaused(mContext);
                        CreateAlarm(mContext,30,intent1);
                        break;
                    case 3:
                        AlarmPaused(mContext);
                        CreateAlarm(mContext,40,intent1);
                        break;
                    case 4:
                        AlarmPaused(mContext);
                        CreateAlarm(mContext,50,intent1);
                        break;
                    case 6:
                        AlarmPaused(mContext);
                        CreateAlarm(mContext,60,intent1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();

    }


    public void CreateAlarm(Context context,int req, Intent intent){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,req,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("req",req);
        context.sendBroadcast(intent);
        setAlarm(pendingIntent,alarmManager);
    }
    public void AlarmPaused(Context context){
        AlarmPauseUtil(10);
        AlarmPauseUtil(20);
        AlarmPauseUtil(30);
        AlarmPauseUtil(40);
        AlarmPauseUtil(50);
        AlarmPauseUtil(60);

    }
    public void AlarmPauseUtil(int req){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,req,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmPause(pendingIntent);

    }
    public void AlarmPause(PendingIntent pendingIntent){
        alarmManager.cancel(pendingIntent);
    }

    public void setAlarm(PendingIntent pendingIntent,AlarmManager alarmManager){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pendingIntent);
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pendingIntent);
        }

    }

}
