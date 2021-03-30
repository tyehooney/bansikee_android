package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.DELETE

interface MyPageRetrofitInterface {

    @DELETE("/deleteUser")
    fun withdrawal(): Call<DefaultResponse>

}