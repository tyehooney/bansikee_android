package com.tomasandfriends.bansikee.src.activities.login.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class LoginResponse : DefaultResponse() {

    @SerializedName("data")
    var data = ""

}