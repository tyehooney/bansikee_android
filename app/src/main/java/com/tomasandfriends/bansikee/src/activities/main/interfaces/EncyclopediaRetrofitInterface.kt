package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.common.models.RecommendationResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EncyclopediaRetrofitInterface {

    //searching plants
    @GET("/plants")
    fun getSearchedPlants(@Query("keyWord") searchingWord: String,
                          @Query("pageNum") pageNum: Int,
                          @Query("sortBy") sortBy: String): Call<RecommendationResponse>

    //get recently searched plants
    @GET("/plants-search")
    fun getRecentlySearchedPlants(): Call<RecommendationResponse>

    //delete all searched plants
    @DELETE("/plant-histories")
    fun deleteAllSearchedPlants(): Call<DefaultResponse>

    //delete searched plant
    @DELETE("plant-history/{plantidx}")
    fun deleteSearchedPlant(@Path("plantidx") plantIdx: Int): Call<DefaultResponse>

}