package com.tomasandfriends.bansikee.src.activities.main.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class HomeResponse: DefaultResponse(){

    @SerializedName("data")
    val homeData = HomeData()

}

class HomeData {

    @SerializedName("userId")
    val userId = 0

    @SerializedName("userName")
    val userName = ""

    @SerializedName("userProfile")
    val userImg = ""

    @SerializedName("greeting")
    val greeting = ""

    @SerializedName("recommendPlantId")
    val recommendedPlantIdx = 0

    @SerializedName("recommendPlantImg")
    val recommendedPlantImg = ""

    @SerializedName("recommendPlantName")
    val recommendedPlantName = ""

    @SerializedName("recommendPlantSpices")
    val recommendedPlantSpecies = ""

    @SerializedName("recommendPlantInfo")
    val recommendedPlantInfo = ""

    @SerializedName("myPlants")
    val myPlants = ArrayList<HomeMyPlantData>()

}

class HomeMyPlantData {

    @SerializedName("myPlantId")
    val myPlantIdx = 0

    @SerializedName("myPlantNickName")
    val nickname = ""

    @SerializedName("myPlantImgUrl")
    val imgUrl = ""

    @SerializedName("myPlantAge")
    val age = ""

    @SerializedName("myPlantSpecies")
    val species = ""

    @SerializedName("myPlantWaterCycle")
    val waterCycle = 0

    @SerializedName("myPlantLastWaterDay")
    val lastWateredDay = ""

    @SerializedName("waterDayFrom")
    val wateredDayFrom = 0

    @SerializedName("waterDayTo")
    val wateredDayTo = 0

    @SerializedName("todayDiaryStatus")
    val didWriteDiaryToday = false

}