package com.example.dasom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.example.dasom.databinding.ActivityMainBinding;
import com.example.dasom.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    private ActivitySignupBinding binding;
    String phoneNumber, pw;
    private final static String TAG = "asdasd";
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);


        binding.setText("PIN번호를 입력해주세요");

        //전화번호
        phoneNumber = "01024469844";


        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);





    }
    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.e(TAG, "Pin complete: " + pin);

            if (pw!=null){
                if (pw.equals(pin)){
                    NetworkHelper.getInstance().SignUp(phoneNumber,pw).enqueue(new Callback<UserJoin>() {
                        @Override
                        public void onResponse(Call<UserJoin> call, Response<UserJoin> response) {
                            Log.e("asdd",response.code()+"");
                            UserJoin userJoin = response.body();
                            Log.e("asd",userJoin.getMessage());
                            Intent intent1 = new Intent(Signup.this,LoginActivity.class);
                            startActivity(intent1);


                        }
                        @Override
                        public void onFailure(Call<UserJoin> call, Throwable t) {
                            Log.e("asdd","실패");
                        }
                    });
                }else{
                    Toast.makeText(Signup.this, "비밀번호 틀림", Toast.LENGTH_SHORT).show();
                    binding.setText("PIN번호를 다시 입력해주세요");
                    mPinLockView.resetPinLockView();
                }



            }else{
                pw = pin;
                binding.setText("PIN번호를 다시 입력해주세요");
                mPinLockView.resetPinLockView();
            }




        }

        @Override
        public void onEmpty() {
            Log.e(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.e(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };
}