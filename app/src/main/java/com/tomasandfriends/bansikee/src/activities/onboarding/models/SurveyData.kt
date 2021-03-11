package com.tomasandfriends.bansikee.src.activities.onboarding.models

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class GetSurveyResponse : DefaultResponse() {

    @SerializedName("data")
    var surveyDataList = ArrayList<SurveyData>()

}

class SurveyData {

    @SerializedName("questionIdx")
    var questionIndex = 0

    @SerializedName("questionContent")
    var mainQuestion = ""

    @SerializedName("info")
    var subQuestion = ""

    @SerializedName("resOptionList")
    var options = ArrayList<OptionData>()

    val selectedIdx = MutableLiveData(-1)
}

class OptionData {

    @SerializedName("optionsIdx")
    var optionIdx = 0

    @SerializedName("content")
    var content = ""

}