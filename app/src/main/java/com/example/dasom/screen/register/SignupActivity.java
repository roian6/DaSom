package com.example.dasom.screen.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.dasom.R;
import com.example.dasom.api.NetworkHelper;
import com.example.dasom.databinding.ActivitySignupBinding;
import com.example.dasom.screen.login.LoginActivity;
import com.example.dasom.util.PhoneUtil;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private String pw;
    private PinLockView mPinLockView;

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        binding.setText("다솜을 사용하려면, \n먼저 4자리의 숫자(PIN 번호)를 입력해야 해요.");

        IndicatorDots mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        binding.btnSignupDebug.setOnClickListener(v -> signUp("010-1111-1111", "1234"));

    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {

            if (pw == null) {
                pw = pin;
                wrongPIN();
                return;
            }

            if (!pw.equals(pin)) {
                Toast.makeText(SignupActivity.this, "앞서 입력한 숫자와 일치하지 않아요.", Toast.LENGTH_SHORT).show();
                wrongPIN();
                return;
            }

            signUp(PhoneUtil.getPhone(SignupActivity.this), pw);

        }

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };

    private void signUp(String phone, String pw) {
        NetworkHelper.getInstance(getString(R.string.base_url)).SignUp(phone, pw)
                .enqueue(new Callback<UserJoin>() {
                    @Override
                    public void onResponse(@NotNull Call<UserJoin> call, @NotNull Response<UserJoin> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UserJoin> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void wrongPIN() {
        binding.setText("확인을 위해,\n4자리의 숫자(PIN 번호)를 다시 입력해주세요.");
        mPinLockView.resetPinLockView();
    }
}