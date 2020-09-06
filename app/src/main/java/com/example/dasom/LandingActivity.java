package com.example.dasom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.CheckId;
import com.example.dasom.fragment.LandingFragment;
import com.example.dasom.util.SharedPreferenceUtil;
import com.github.paolorotolo.appintro.AppIntro;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;
import java.util.Locale;

public class LandingActivity extends AppIntro {

    private int check;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(LandingFragment.newInstance(R.layout.activity_landing1));
        addSlide(LandingFragment.newInstance(R.layout.activity_landing2));
        addSlide(LandingFragment.newInstance(R.layout.activity_landing3));

        showSkipButton(false);
        setProgressButtonEnabled(true);
        showSeparator(false);
        setIndicatorColor(getColor(R.color.colorPrimary), getColor(R.color.materialGray5));
        setImageNextButton(ContextCompat.getDrawable(this, R.drawable.ic_navigate_next_primary_24dp));
        setColorTransitionsEnabled(true);
        setDoneText("시작하기");
        setColorDoneText(getColor(R.color.colorPrimary));
        setDoneTextTypeface(R.font.apple_sd_gothic_neo_h);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        finishLanding();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        finishLanding();
    }

    private void Check(String phoneNumber){
        NetworkHelper.getInstance().CheckID(phoneNumber).enqueue(new Callback<CheckId>() {
            @Override
            public void onResponse(Call<CheckId> call, Response<CheckId> response) {

                if (response.isSuccessful()){
                    startActivity(new Intent(LandingActivity.this,SignupActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(LandingActivity.this,LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CheckId> call, Throwable t) {
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void finishLanding() {
        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                SharedPreferenceUtil.putBoolean(LandingActivity.this, "landing_shown", true);

                TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                phoneNumber = tMgr.getLine1Number();

                if (phoneNumber.startsWith("+82"))
                    phoneNumber = phoneNumber.replace("+82", "0"); // +8210xxxxyyyy 로 시작되는 번호

                phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());

                Check(phoneNumber);

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
    }
}
