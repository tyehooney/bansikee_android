package com.tomasandfriends.bansikee.src.activities.main.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class HomeResponse: DefaultResponse(){

    @SerializedName("data")
    var homeData = HomeData()

}

class HomeData {

    @SerializedName("userId")
    var userId = 0

    @SerializedName("userName")
    var userName = ""

    @SerializedName("userProfile")
    var userImg = ""

    @SerializedName("greeting")
    var greeting = ""

    @SerializedName("recommendPlantId")
    var recommendedPlantIdx = 0

    @SerializedName("recommendPlantImg")
    var recommendedPlantImg = ""

    @SerializedName("recommendPlantName")
    var recommendedPlantName = ""

    @SerializedName("recommendPlantSpices")
    var recommendedPlantSpecies = ""

    @SerializedName("recommendPlantInfo")
    var recommendedPlantInfo = ""

    @SerializedName("myPlants")
    var myPlants = ArrayList<HomeMyPlantData>()

}

class HomeMyPlantData {

    @SerializedName("myPlantId")
    var myPlantIdx = 0

    @SerializedName("myPlantNickName")
    var nickname = ""

    @SerializedName("myPlantImgUrl")
    var imgUrl = ""

    @SerializedName("myPlantAge")
    var age = ""

    @SerializedName("myPlantSpecies")
    var species = ""

    @SerializedName("myPlantWaterCycle")
    var waterCycle = 0

    @SerializedName("myPlantLastWaterDay")
    var lastWateredDay = ""

    @SerializedName("waterDayFrom")
    var wateredDayFrom = 0

    @SerializedName("waterDayTo")
    var wateredDayTo = 0

    @SerializedName("todayDiaryStatus")
    var didWriteDiaryToday = false

}