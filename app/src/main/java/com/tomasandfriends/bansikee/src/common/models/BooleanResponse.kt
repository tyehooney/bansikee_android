package com.tomasandfriends.bansikee.src.common.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class BooleanResponse: DefaultResponse() {
    @SerializedName("data")
    var data = false
}