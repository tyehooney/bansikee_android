package com.tomasandfriends.bansikee.src.common.services

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.common.interfaces.CheckNicknameView
import com.tomasandfriends.bansikee.src.common.interfaces.CommonRetrofitInterface
import com.tomasandfriends.bansikee.src.common.models.BooleanResponse
import com.tomasandfriends.bansikee.src.common.models.NicknameBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckNicknameService(checkNicknameView: CheckNicknameView) {
    private val mCheckNicknameView = checkNicknameView

    fun checkNickname(nickname: String){
        val retroInterface = ApplicationClass.initRetrofit().create(CommonRetrofitInterface::class.java)

        retroInterface.checkNickname(NicknameBody(nickname)).enqueue(object:
            Callback<BooleanResponse> {
            override fun onResponse(call: Call<BooleanResponse>, response: Response<BooleanResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mCheckNicknameView.checkNicknameSuccess(apiResponse!!.data)
                } else {
                    mCheckNicknameView.checkNicknameFailed(
                        if(response.body() == null)
                            ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                        else
                            response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                mCheckNicknameView.checkNicknameFailed(null)
            }
        })
    }
}