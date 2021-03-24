package com.tomasandfriends.bansikee.src.activities.main.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class MyPlantsResponse: DefaultResponse() {

    @SerializedName("listData")
    var myPlantDataList = ArrayList<MyPlantData>()

}