package com.tomasandfriends.bansikee.src.activities.main.models

import com.google.gson.annotations.SerializedName

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

}