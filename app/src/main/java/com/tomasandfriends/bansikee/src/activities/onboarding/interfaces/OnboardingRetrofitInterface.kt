package com.tomasandfriends.bansikee.src.activities.onboarding.interfaces

import com.tomasandfriends.bansikee.src.activities.onboarding.models.GetSurveyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface OnboardingRetrofitInterface {
    //get survey data
    @GET("/on-boarding")
    fun getSurvey() : Call<GetSurveyResponse>
}