package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.activities.main.models.MyPlantsResponse
import retrofit2.Call
import retrofit2.http.GET

interface MyGardenRetrofitInterface {

    @GET("/myplant")
    fun getMyPlants(): Call<MyPlantsResponse>

}