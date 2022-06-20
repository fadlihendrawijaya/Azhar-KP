package com.malikaproject.ecommerceapp.network

import com.malikaproject.ecommerceapp.model.ResponseData
import com.malikaproject.ecommerceapp.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClient {

    @FormUrlEncoded
    @POST("coffeeshop/login.php")
    fun login(
        @Field("postid") id : String,
        @Field("postpass") pass : String
    ): Call<ResponseLogin>
    @POST("coffeeshop/read.php")
    fun read(
        @Field("postid") id : String
    ): Call<ResponseData>
}