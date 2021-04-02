package com.tomasandfriends.bansikee.src.activities.main.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class MyPlantsResponse: DefaultResponse() {

    @SerializedName("listData")
    var myPlantDataList = ArrayList<MyPlantData>()

}

class MyPlantData {

    @SerializedName("contents")
    var contents = ""

    @SerializedName("myPlantId")
    var plantIdx = 0

    @SerializedName("nickName")
    var nickname = ""

    @SerializedName("plantName")
    var plantName = ""

    @SerializedName("profileImgUrl")
    var imgUrl = ""

    @SerializedName("waterDaysFrom")
    var waterDaysFrom = 0

    @SerializedName("waterDaysTo")
    var waterDaysTo = 0
}