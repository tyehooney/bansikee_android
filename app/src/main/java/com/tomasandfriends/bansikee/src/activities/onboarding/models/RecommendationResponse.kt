package com.tomasandfriends.bansikee.src.activities.onboarding.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.DefaultResponse
import com.tomasandfriends.bansikee.src.common.models.PlantData

class RecommendationResponse: DefaultResponse(){

    @SerializedName("data")
    var surveyResultDataList = ArrayList<PlantData>()

}