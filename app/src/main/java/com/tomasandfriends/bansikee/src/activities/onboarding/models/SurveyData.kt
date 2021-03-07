package com.tomasandfriends.bansikee.src.activities.onboarding.models

import androidx.lifecycle.MutableLiveData

class SurveyData(val title: String,
                 val subTitle: String,
                 val answers: List<String>) {

    val selectedIdx = MutableLiveData(-1)
}