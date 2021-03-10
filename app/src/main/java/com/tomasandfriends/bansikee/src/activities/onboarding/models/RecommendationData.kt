package com.tomasandfriends.bansikee.src.activities.onboarding.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.DefaultResponse

class RecommendationResponse: DefaultResponse(){

    @SerializedName("data")
    var surveyResultDataList = ArrayList<RecommendationData>()

}

class RecommendationData{

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