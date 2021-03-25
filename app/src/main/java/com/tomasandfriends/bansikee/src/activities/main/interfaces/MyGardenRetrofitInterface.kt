package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.activities.main.models.MyPlantsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MyGardenRetrofitInterface {

    @GET("/myplant")
    fun getMyPlants(): Call<MyPlantsResponse>

    @DELETE("/myplant/delete/{myPlantId}")
    fun deleteMyPlant(@Path("myPlantId") myPlantIdx: Int): Call<DefaultResponse>
}