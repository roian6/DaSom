package com.example.dasom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.CheckId;
import com.example.dasom.util.SharedPreferenceUtil;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private String phoneNumber;

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber = tMgr.getLine1Number();

        if (phoneNumber.startsWith("+82"))
            phoneNumber = phoneNumber.replace("+82", "0"); // +8210xxxxyyyy 로 시작되는 번호

        phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());

        NetworkHelper.getInstance().CheckID(phoneNumber).enqueue(new Callback<CheckId>() {
            @Override
            public void onResponse(Call<CheckId> call, Response<CheckId> response) {
                if (response.code()==200){
                    CheckId checkId = response.body();
                }
            }

            @Override
            public void onFailure(Call<CheckId> call, Throwable t) {

            }
        });




        new Handler(Looper.getMainLooper()).postDelayed(() -> {



            if (UserCache.getUser(this)!= null&&TokenCache.getToken(this)!=null){
                Intent intent1 = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent1);
            }

            boolean isLandingShown = getSharedPreferences(this).getBoolean("landing_shown", false);
            isLandingShown = false; //remove this line, to show landing page only once

            if (isLandingShown)
                if (UserCache.getUser(this) != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            else {
                getSharedPreferences(this)
                        .edit()
                        .putBoolean("landing_shown", true)
                        .apply();
                startActivity(new Intent(SplashActivity.this, LandingActivity.class));
            }

            finish();
        }, 2000); //wait 2 sec
    }

}
