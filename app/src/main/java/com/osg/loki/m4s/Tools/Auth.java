package com.osg.loki.m4s.Tools;

import com.osg.loki.m4s.Model.AccountCredentials;
import com.osg.loki.m4s.Model.Token;
import com.osg.loki.m4s.Model.UserRegisterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ergas on 5/28/2018.
 */

public interface Auth {
    @POST("/api/login/")
    Call<Token> login(@Body AccountCredentials user);
    @POST("/api/signup/")
    Call<ResponseBody> signup(@Body UserRegisterModel user);
    @GET("/api/confirm/")
    Call<Token> confirm(@Query("username") String username,@Query("confirm_code")String confirm_code);

    @GET("/api/password-recover/")
    Call<Void> checkPhone(@Query("phone")String phone);
    @GET("/api/password-recover/")
    Call<Void> confirmPhone(@Query("phone")String phone,@Query("code")String confirm_code);
    @GET("/api/password-recover/")
    Call<Void> newPasswd(@Query("phone")String phone,@Query("password")String password);

}
