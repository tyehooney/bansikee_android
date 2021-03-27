package com.tomasandfriends.bansikee.src.activities.diary.interfaces

import com.tomasandfriends.bansikee.src.activities.diary.models.AddDiaryBody
import com.tomasandfriends.bansikee.src.activities.diary.models.DiaryDetailsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryRetrofitInterface {

    @GET("/diary/plantDiary/{diaryId}")
    fun getDiaryDetails(@Path("diaryId") diaryIdx: Int): Call<DiaryDetailsResponse>

    @POST("/registration/diary")
    fun addDiary(@Body addDiaryBody: AddDiaryBody): Call<DefaultResponse>

}