package com.jamalonexpress.testhcut;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("lat")
    Call<List<Provider>> getLatLng();

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginPost(
      @Field("email") String email,
      @Field("password") String password
    );

    // @FormUrlEncoded
    @POST("register")
    Call<Register> registerPost(@Body Register requestObj);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerPost(@FieldMap Map<String, String> fields);

    @POST("login")
    Call<ResponseBody> createPost(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("login")
    Call<Login> savePost(@Field("title") String title,
                        @Field("body") String body);
}
