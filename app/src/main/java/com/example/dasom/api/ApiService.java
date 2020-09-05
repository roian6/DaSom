package com.example.dasom.api;

import com.example.dasom.data.UserJoin;
import com.example.dasom.data.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @POST("auth/signin")
    @FormUrlEncoded
    Call<UserLogin> SignIn(
            @Field("id") String id,
            @Field("password") String password

    );

    @POST("auth/signup")
    @FormUrlEncoded
    Call<UserJoin> SignUp(
        @Field("id")String id,
        @Field("password")String password,
        @Field("username")String name
    );

}
