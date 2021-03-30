package com.tomasandfriends.bansikee.src.common.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.common.models.PlantData

class PlantsResponse: DefaultResponse(){

    @SerializedName("data")
    var plantDataList = ArrayList<PlantData>()

}