package com.example.dasom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.UserJoin;
import com.example.dasom.databinding.ActivitySignupBinding;
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
        binding.setText("PIN 번호를 입력해주세요.");

        IndicatorDots mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

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
                Toast.makeText(SignupActivity.this, "PIN이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                wrongPIN();
                return;
            }

            NetworkHelper.getInstance().SignUp(PhoneUtil.getPhone(SignupActivity.this), pw)
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

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };

    private void wrongPIN(){
        binding.setText("PIN 번호를 다시 입력해주세요.");
        mPinLockView.resetPinLockView();
    }
}