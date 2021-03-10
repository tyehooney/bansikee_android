package com.tomasandfriends.bansikee.src.activities.main.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.DefaultResponse

class IsOnboardedResponse: DefaultResponse() {
    @SerializedName("data")
    var onboarded = false
}