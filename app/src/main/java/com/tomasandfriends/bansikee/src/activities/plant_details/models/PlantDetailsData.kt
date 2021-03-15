package com.tomasandfriends.bansikee.src.activities.plant_details.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class PlantDetailsResponse : DefaultResponse() {

    @SerializedName("data")
    val plantDetailsData = PlantDetailsData()

}

class PlantDetailsData {

    @SerializedName("area")
    val area = ""

    @SerializedName("height")
    val height = 0

    @SerializedName("info")
    val info = ""

    @SerializedName("light")
    val light = ""

    @SerializedName("like")
    val like = false

    @SerializedName("managementLevel")
    val managementLevel = ""

    @SerializedName("name")
    val name = ""

    @SerializedName("plantIdx")
    val plantIdx = 0;

    @SerializedName("plantImgUrl")
    val plantImgUrl = ""

    @SerializedName("smell")
    val smell = ""

    @SerializedName("species")
    val species = ""

    @SerializedName("speed")
    val speed = ""

    @SerializedName("summerWater")
    val summerWater = ""

    @SerializedName("winterWater")
    val winterWater = ""

    @SerializedName("temperature")
    val temperature = ""

    @SerializedName("width")
    val width = 0

}