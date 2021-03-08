package com.tomasandfriends.bansikee.src.activities.login.interfaces

import com.tomasandfriends.bansikee.src.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.login.models.AccessObject
import com.tomasandfriends.bansikee.src.activities.login.models.LoginBody
import com.tomasandfriends.bansikee.src.activities.login.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    //Google Login
    @POST("/signup/google")
    fun googleLogin(@Body idToken : AccessObject) : Call<LoginResponse>

    //Kakao Login
    @POST("/signup/kakao")
    fun kakaoLogin(@Body accessToken: AccessObject) : Call<LoginResponse>

    //Basic Login
    @POST("/v1/signin")
    fun basicLogin(@Body emailAndPassword: LoginBody) : Call<LoginResponse>
}