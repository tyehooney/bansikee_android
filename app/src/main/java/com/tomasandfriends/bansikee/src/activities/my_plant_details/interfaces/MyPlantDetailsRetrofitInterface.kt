package com.tomasandfriends.bansikee.src.activities.my_plant_details.interfaces

import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.DiaryListResponse
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.MyPlantDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPlantDetailsRetrofitInterface {

    @GET("/myplant/{myPlantId}")
    fun getMyPlantDetails(@Path("myPlantId") myPlantIdx: Int): Call<MyPlantDetailsResponse>

    @GET("/diary/{myPlantId}")
    fun getDiaryList(@Path("myPlantId") myPlantIdx: Int): Call<DiaryListResponse>

}