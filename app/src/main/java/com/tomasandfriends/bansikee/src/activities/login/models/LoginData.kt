package com.tomasandfriends.bansikee.src.activities.login.models

import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse

class LoginResponse : DefaultResponse() {

    @SerializedName("data")
    val data = LoginData()

}

class LoginData {

    @SerializedName("email")
    val email = ""

    @SerializedName("jwt")
    val jwt = ""

    @SerializedName("name")
    val name = ""

}

class AccessObject(private val accessToken: String)

class LoginBody(private val email: String, private val password: String)