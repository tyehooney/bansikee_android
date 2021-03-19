package com.tomasandfriends.bansikee.src.activities.plant_details.interfaces

import com.tomasandfriends.bansikee.src.activities.plant_details.models.PlantDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantDetailsRetrofitInterface {

    @GET("/plant/{plantIdx}")
    fun getPlantDetails(@Path("plantIdx") plantIdx: Int,
                        @Query("status") status: String?): Call<PlantDetailsResponse>

}