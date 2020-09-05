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

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.UserJoin;
import com.example.dasom.databinding.ActivityMainBinding;
import com.example.dasom.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    private ActivitySignupBinding binding;
    String phoneNumber, pw, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);


        //전화번호
        phoneNumber = "01024169913";





        binding.signUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw = binding.pw.getText().toString();
                if (binding.checkPw.getText().toString() == pw){

                    NetworkHelper.getInstance().SignUp(phoneNumber,pw).enqueue(new Callback<UserJoin>() {
                        @Override
                        public void onResponse(Call<UserJoin> call, Response<UserJoin> response) {
                            UserJoin userJoin = response.body();
                            Log.e("asd",userJoin.getMessage());
                            Intent intent1 = new Intent(Signup.this,LoginActivity.class);
                            startActivity(intent1);


                        }
                        @Override
                        public void onFailure(Call<UserJoin> call, Throwable t) {
                        }
                    });

                }else{
                    Toast.makeText(Signup.this, "pin번호틀림", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}