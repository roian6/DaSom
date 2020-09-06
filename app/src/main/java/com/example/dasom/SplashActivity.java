package com.example.dasom;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.CheckId;
import com.example.dasom.util.PhoneUtil;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            PermissionListener listener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    findLoginMethod();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    finish();
                }
            };

            TedPermission.with(this)
                    .setPermissionListener(listener)
                    .setDeniedMessage("권한에 동의해 주세요.")
                    .setPermissions(Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE)
                    .check();

        }, 2000); //wait 2 sec
    }

    private void findLoginMethod() {
        if (UserCache.getUser(this) != null && TokenCache.getToken(this) != null) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            checkRegistered(PhoneUtil.getPhone(this), this);
        }
    }

    private void checkRegistered(String phoneNumber, Context context) {
        NetworkHelper.getInstance().CheckID(phoneNumber).enqueue(new Callback<CheckId>() {
            @Override
            public void onResponse(@NotNull Call<CheckId> call, @NotNull Response<CheckId> response) {
                boolean isLandingShown = getSharedPreferences(context).getBoolean("landing_shown", false);
                //isLandingShown = false; //remove this line, to show landing page only once

                boolean isRegistered = response.isSuccessful();

                if (isLandingShown)
                    startActivity(new Intent(SplashActivity.this,
                            isRegistered ? LoginActivity.class : SignupActivity.class));
                else {
                    Intent intent = new Intent(SplashActivity.this, LandingActivity.class);
                    intent.putExtra("is_registered", isRegistered);
                    startActivity(intent);
                }

                finish();
            }

            @Override
            public void onFailure(@NotNull Call<CheckId> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
