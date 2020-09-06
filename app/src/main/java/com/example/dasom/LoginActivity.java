package com.example.dasom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.UserLogin;
import com.example.dasom.databinding.ActivityLoginBinding;
import com.example.dasom.util.PhoneUtil;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);

        IndicatorDots mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        PinLockView mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            NetworkHelper.getInstance().SignIn(PhoneUtil.getPhone(LoginActivity.this), pin)
                    .enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    if (response.isSuccessful()) {
                        UserLogin userLogin = response.body();

                        TokenCache.setToken(LoginActivity.this, userLogin.getAccessToken());
                        UserCache.setUser(LoginActivity.this, PhoneUtil.getPhone(LoginActivity.this));

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "PIN이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserLogin> call, @NotNull Throwable t) {
                    t.printStackTrace();
                }
            });

        }
        @Override
        public void onEmpty() {

        }
        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };

}