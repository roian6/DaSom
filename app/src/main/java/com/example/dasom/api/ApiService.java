package com.example.dasom.api;

import com.example.dasom.screen.chat.ChatBody;
import com.example.dasom.screen.splash.CheckId;
import com.example.dasom.screen.register.UserJoin;
import com.example.dasom.screen.login.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
            @Field("id") String id,
            @Field("password") String password
    );

    @GET("auth/check-id")
    Call<CheckId> CheckID(
            @Query("id") String id
    );

//    @POST("diary/chatbot")
//    Call<ChatBody> sendChat(
//            @Header("Content-Type") String contentType,
//            @Body ChatModel chatModel
//    );


    @POST("diary/chatbot")
    @FormUrlEncoded
    Call<ChatBody> sendChat(
            @Header("Authorization") String authorization,
            @Field("date") String date,
            @Field("time") String time,
            @Field("text") String text
    );
}
