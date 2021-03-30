package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MyPageRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MyPageView
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(myPageView: MyPageView) {
    private val mMyPageView = myPageView

    fun withdrawal(){
        val retrofitInterface = initRetrofit().create(MyPageRetrofitInterface::class.java)

        retrofitInterface.withdrawal().enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMyPageView.withdrawalSuccess(apiResponse!!.detail)
                } else {
                    mMyPageView.withdrawalFailed(
                        if(response.body() == null)
                            ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                        else
                            response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mMyPageView.withdrawalFailed(null)
            }
        })
    }
}