package com.example.dasom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.data.UserJoin;
import com.example.dasom.data.UserLogin;
import com.example.dasom.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private String id,pw;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent1 = new Intent(LoginActivity.this,Signup.class);
                startActivity(intent1);

            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = binding.id.getText().toString();
                pw = binding.password.getText().toString();

                NetworkHelper.getInstance().SignIn(id,pw).enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                        UserLogin userLogin = response.body();
                        Log.e("asd",response.code()+"");
                        Log.e("asd",userLogin.getMessage());
                        login();

                    }

                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {

                    }
                });

            }
        });

    }

    public void login(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}