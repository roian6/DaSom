package com.example.dasom.api;

import com.example.dasom.screen.chat.ChatBody;
import com.example.dasom.screen.login.UserLogin;
import com.example.dasom.screen.main1.DiaryBody;
import com.example.dasom.screen.main2.CountData;
import com.example.dasom.screen.main2.SettingData;
import com.example.dasom.screen.main2.UpdateInfo;
import com.example.dasom.screen.main2.UpdateInfoValue;
import com.example.dasom.screen.register.UserJoin;
import com.example.dasom.screen.splash.CheckId;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @PUT("auth/update_info")
    Call<UpdateInfo> UpdateInfo(
            @Header("Authorization") String authorization,
            @Body UpdateInfoValue updateInfoValue
    );

    @Multipart
    @POST("diary/chatbot")
    Call<ChatBody> sendChat(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part photo,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("text") RequestBody text
    );

    @GET("diary/get-all")
    Call<DiaryBody> getAllDiary(
            @Header("Authorization") String authorization
    );

    @GET("diary/get-when-to-write")
    Call<SettingData> updateInfo(
            @Header("Authorization") String authorization
    );
}
