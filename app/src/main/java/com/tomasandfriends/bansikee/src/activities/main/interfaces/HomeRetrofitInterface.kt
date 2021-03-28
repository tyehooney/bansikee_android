package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.activities.main.models.HomeResponse
import retrofit2.Call
import retrofit2.http.POST

interface HomeRetrofitInterface {

    @POST("/home")
    fun getHomeData(): Call<HomeResponse>

}