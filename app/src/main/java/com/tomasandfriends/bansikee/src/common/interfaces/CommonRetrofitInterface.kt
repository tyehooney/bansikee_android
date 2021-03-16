package com.tomasandfriends.bansikee.src.common.interfaces

import com.tomasandfriends.bansikee.src.common.models.RecommendationResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommonRetrofitInterface {

    //get recommendations
    @GET("/recommendation")
    fun getRecommendations(): Call<RecommendationResponse>

    //change plant like status
    @POST("/plant/{plantIdx}/like")
    fun changePlantLike(@Path("plantIdx") plantIdx: Int): Call<DefaultResponse>
}