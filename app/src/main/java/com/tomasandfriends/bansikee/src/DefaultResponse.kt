package com.tomasandfriends.bansikee.src

import com.google.gson.annotations.SerializedName

open class DefaultResponse {
    @SerializedName("success")
    var success = false
        protected set
    @SerializedName("code")
    var code = 0
        protected set
    @SerializedName("data")
    var data = ""
        protected set
    @SerializedName("msg")
    var msg = ""
        protected set
}