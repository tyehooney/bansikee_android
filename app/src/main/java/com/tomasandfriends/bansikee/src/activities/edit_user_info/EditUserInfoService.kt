package com.tomasandfriends.bansikee.src.activities.edit_user_info

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.edit_user_info.interfaces.EditUserInfoRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.edit_user_info.interfaces.EditUserInfoView
import com.tomasandfriends.bansikee.src.activities.edit_user_info.models.EditUserInfoBody
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserInfoService(editUserInfoView: EditUserInfoView) {
    private val mEditUserInfoView = editUserInfoView

    fun editUserInfo(nickname: String, profileImgUrl: String){
        val retrofitInterface = initRetrofit().create(EditUserInfoRetrofitInterface::class.java)

        retrofitInterface.editUserInfo(EditUserInfoBody(nickname, profileImgUrl))
            .enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mEditUserInfoView.editUserInfoSuccess(apiResponse!!.detail)
                } else {
                    mEditUserInfoView.editUserInfoFailed(
                        if(response.body() == null)
                            ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                        else
                            response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mEditUserInfoView.editUserInfoFailed(null)
            }
        })
    }
}