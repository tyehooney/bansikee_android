package com.tomasandfriends.bansikee.src.activities.diary.interfaces

import com.tomasandfriends.bansikee.src.activities.diary.models.DiaryDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DiaryRetrofitInterface {

    @GET("/diary/plantDiary/{diaryId}")
    fun getDiaryDetails(@Path("diaryId") diaryIdx: Int): Call<DiaryDetailsResponse>

}