package com.tomasandfriends.bansikee.src.activities.sign_up.interfaces

import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.sign_up.models.SignUpBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpRetrofitInterface {

    //Sign Up
    @POST("/v1/signup")
    fun signUp(@Body signUpBody : SignUpBody): Call<DefaultResponse>
}