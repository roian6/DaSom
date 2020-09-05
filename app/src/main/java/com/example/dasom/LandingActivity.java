package com.example.dasom;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.dasom.fragment.LandingFragment;
import com.example.dasom.util.SharedPreferenceUtil;
import com.github.paolorotolo.appintro.AppIntro;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class LandingActivity extends AppIntro {

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


    private void finishLanding() {
        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                SharedPreferenceUtil.putBoolean(LandingActivity.this, "landing_shown", true);
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                finish();
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
}
