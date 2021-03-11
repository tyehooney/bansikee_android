package com.tomasandfriends.bansikee.src.activities.sign_up.interfaces

import com.tomasandfriends.bansikee.src.activities.sign_up.models.NicknameBody
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.sign_up.models.SignUpBody
import com.tomasandfriends.bansikee.src.common.models.BooleanResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpRetrofitInterface {

    //Check Nickname
    @POST("/v1/nickNameDuplicate")
    fun checkNickname(@Body nicknameBody: NicknameBody): Call<BooleanResponse>

    //Sign Up
    @POST("/v1/signup")
    fun signUp(@Body signUpBody : SignUpBody): Call<DefaultResponse>
}