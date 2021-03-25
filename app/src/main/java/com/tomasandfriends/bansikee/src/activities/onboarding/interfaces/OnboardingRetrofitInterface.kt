package com.tomasandfriends.bansikee.src.activities.onboarding.interfaces

import com.tomasandfriends.bansikee.src.activities.onboarding.models.AnswerBody
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.onboarding.models.GetSurveyResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnboardingRetrofitInterface {
    //get survey data
    @GET("/on-boarding")
    fun getSurvey(): Call<GetSurveyResponse>

    //post answers
    @POST("/on-boarding")
    fun answerSurvey(@Body reqAnswerDto: List<AnswerBody>): Call<DefaultResponse>
}