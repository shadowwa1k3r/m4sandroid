package com.osg.loki.m4s.Tools;

import com.osg.loki.m4s.DataBase.NewTest;
import com.osg.loki.m4s.Model.ReportAnswer;
import com.osg.loki.m4s.Model.Wikimodel;
import com.osg.loki.m4s.Model.alertDataModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ergas on 2/20/2018.
 */

public interface Urls {

@GET("reverse")
    Call<addressModel> getAddress(@Query("format") String format,@Query("lat") double altitude,@Query("lon") double longitude,@Query("accept-language") String language);
@Multipart
@POST("/api/alert/")
    Call<ReportAnswer> postReport(@Header("Authorization")String token, @Part("data") alertDataModel data, @Part List<MultipartBody.Part> image);
@GET("/api/syncwiki/")
    Call<List<Wikimodel>> syncwiki(@Header("Authorization")String token, @Query("dbVersion")int dbversion);
    @GET("/api/test/list/")
    Call<List<NewTest>> getTestList();

}
