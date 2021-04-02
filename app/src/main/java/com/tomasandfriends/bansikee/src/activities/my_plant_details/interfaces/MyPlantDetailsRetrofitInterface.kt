package com.tomasandfriends.bansikee.src.activities.my_plant_details.interfaces

import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.DiaryListResponse
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.MyPlantDetailsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPlantDetailsRetrofitInterface {

    @GET("/myplant/{myPlantId}")
    fun getMyPlantDetails(@Path("myPlantId") myPlantIdx: Int): Call<MyPlantDetailsResponse>

    @GET("/diary/{myPlantId}")
    fun getDiaryList(@Path("myPlantId") myPlantIdx: Int): Call<DiaryListResponse>

    @DELETE("/diary/delete/{diaryId}")
    fun deleteDiary(@Path("diaryId") diaryIdx: Int): Call<DefaultResponse>

}