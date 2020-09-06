package com.example.dasom.api;

import com.example.dasom.data.CheckId;
import com.example.dasom.data.UserJoin;
import com.example.dasom.data.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
        @Field("password")String password
    );

    @GET("auth/check-id")
    Call<CheckId> CheckID(
      @Query("id")String id
    );

}
