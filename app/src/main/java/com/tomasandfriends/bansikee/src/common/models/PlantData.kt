package com.tomasandfriends.bansikee.src.common.models

import com.google.gson.annotations.SerializedName

class PlantData{

    @SerializedName("count")
    var count = 0

    @SerializedName("name")
    var name = ""

    @SerializedName("info")
    var info = ""

    @SerializedName("like")
    var like = false

    @SerializedName("plantIdx")
    var plantIdx = 0

    @SerializedName("plantImgUrl")
    var plantImgUrl = ""

    @SerializedName("species")
    var species = ""

}