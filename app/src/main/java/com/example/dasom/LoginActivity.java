package com.example.dasom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.UserJoin;
import com.example.dasom.data.UserLogin;
import com.example.dasom.databinding.ActivityLoginBinding;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private String id;
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;
    private ActivityLoginBinding binding;
    private final static String TAG = "asdasd";
    private Context mContext;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mContext = getApplicationContext();
        binding.setActivity(this);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();

        if (phoneNumber.startsWith("+82"))
            phoneNumber = phoneNumber.replace("+82", "0"); // +8210xxxxyyyy 로 시작되는 번호

        phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());
        //전화번호 넣어줘
        id = phoneNumber;

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

    }
    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.e(TAG, "Pin complete: " + pin);

            NetworkHelper.getInstance().SignIn(id,pin).enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                    UserLogin userLogin = response.body();
                    Log.e("asd", response.code() + "");

                    if (response.code() == 200) {
                        Log.e("asd", userLogin.getMessage());
                        TokenCache.setToken(mContext, userLogin.getAccessToken());
                        UserCache.setUser(mContext, id);
                        Log.e("asd", TokenCache.getToken(mContext));
                        Log.e("asdd", UserCache.getUser(mContext));
                        login();
                    } else {
                        Toast.makeText(mContext, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {

                }
            });

            }

        @Override
        public void onEmpty() {
            Log.e(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };


    public void login(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}