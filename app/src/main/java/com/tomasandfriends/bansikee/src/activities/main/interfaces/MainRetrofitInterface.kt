package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.activities.main.models.IsOnboardedResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainRetrofitInterface {

    //check isOnboarded
    @GET("/isOnboarded")
    fun isOnboarded(): Call<IsOnboardedResponse>
}