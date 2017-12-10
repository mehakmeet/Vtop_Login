package com.example.mehakmeet.loginadg;

import android.telecom.Call;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by MEHAKMEET on 04-12-2017.
 */

public interface RegisterAPI {

    @FormUrlEncoded
    @POST("/login")
    public void insert(
            @Field("regno") String regno,@Field("password") String password,
            Callback<Response> callback
    );

}
