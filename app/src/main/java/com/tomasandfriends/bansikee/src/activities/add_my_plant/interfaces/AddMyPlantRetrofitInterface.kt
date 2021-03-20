package com.tomasandfriends.bansikee.src.activities.add_my_plant.interfaces

import com.tomasandfriends.bansikee.src.activities.add_my_plant.models.AddPlantBody
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AddMyPlantRetrofitInterface {

    // add to my plants
    @POST("/myplant/registration")
    fun addToMyPlants(@Body addPlantBody: AddPlantBody): Call<DefaultResponse>

}