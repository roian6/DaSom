package com.example.dasom;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.dasom.fragment.LandingFragment;
import com.example.dasom.util.SharedPreferenceUtil;
import com.github.paolorotolo.appintro.AppIntro;

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
        SharedPreferenceUtil.putBoolean(LandingActivity.this, "landing_shown", true);

        boolean isRegistered = getIntent().getBooleanExtra("is_registered", false);
        startActivity(new Intent(LandingActivity.this,
                isRegistered ? LoginActivity.class : SignupActivity.class));

    }
}
