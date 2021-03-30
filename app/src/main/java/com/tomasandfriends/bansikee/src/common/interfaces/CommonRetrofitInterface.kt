package com.tomasandfriends.bansikee.src.common.interfaces

import com.tomasandfriends.bansikee.src.common.models.NicknameBody
import com.tomasandfriends.bansikee.src.common.models.BooleanResponse
import com.tomasandfriends.bansikee.src.common.models.PlantsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommonRetrofitInterface {

    //get recommendations
    @GET("/recommendation")
    fun getRecommendations(): Call<PlantsResponse>

    //get liking plants
    @GET("/plants-like")
    fun getLikingPlants(): Call<PlantsResponse>

    //change plant like status
    @POST("/plant/{plantIdx}/like")
    fun changePlantLike(@Path("plantIdx") plantIdx: Int): Call<DefaultResponse>

    //Check Nickname
    @POST("/v1/nickNameDuplicate")
    fun checkNickname(@Body nicknameBody: NicknameBody): Call<BooleanResponse>
}