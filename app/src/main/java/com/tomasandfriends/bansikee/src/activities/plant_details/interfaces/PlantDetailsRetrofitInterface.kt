package com.tomasandfriends.bansikee.src.activities.plant_details.interfaces

import com.tomasandfriends.bansikee.src.activities.plant_details.models.PlantDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantDetailsRetrofitInterface {

    @GET("/plant/{plantIdx}")
    fun getPlantDetails(@Path("plantIdx") plantIdx: Int): Call<PlantDetailsResponse>

}