package com.example.dasom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.UserJoin;
import com.example.dasom.databinding.ActivityMainBinding;
import com.example.dasom.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    private ActivitySignupBinding binding;
    String id, pw, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);



        binding.signUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = binding.id.getText().toString();
                pw = binding.pw.getText().toString();
                name = binding.name.getText().toString();
                Log.e("asd",id);
                Log.e("asd",pw);
                Log.e("asd",name);
                NetworkHelper.getInstance().SignUp(id,pw,name).enqueue(new Callback<UserJoin>() {
                    @Override
                    public void onResponse(Call<UserJoin> call, Response<UserJoin> response) {
                        UserJoin userJoin = response.body();
                        Log.e("asd",userJoin.getMessage());
                    }
                    @Override
                    public void onFailure(Call<UserJoin> call, Throwable t) {

                    }
                });

            }
        });
    }
}