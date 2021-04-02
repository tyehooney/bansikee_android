package com.tomasandfriends.bansikee.src.activities.edit_user_info.interfaces

import com.tomasandfriends.bansikee.src.activities.edit_user_info.models.EditUserInfoBody
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface EditUserInfoRetrofitInterface {

    @PATCH("/user")
    fun editUserInfo(@Body reqUserDto: EditUserInfoBody): Call<DefaultResponse>

}