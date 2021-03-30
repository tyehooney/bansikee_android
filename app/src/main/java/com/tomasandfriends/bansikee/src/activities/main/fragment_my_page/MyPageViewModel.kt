package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel

class MyPageViewModel : BaseViewModel() {

    private val _goEditMyInfoEvent = SingleLiveEvent<Void?>()
    val goEditMyInfoEvent: LiveData<Void?> = _goEditMyInfoEvent

    fun goEditMyInfoClick(){
        _goEditMyInfoEvent.value = null
    }

    private val _goMyLikingPlantsEvent = SingleLiveEvent<Void?>()
    val goMyLikingPlantsEvent: LiveData<Void?> = _goMyLikingPlantsEvent

    fun goMyLikingPlantsClick(){
        _goMyLikingPlantsEvent.value = null
    }

    private val _logoutClickEvent = SingleLiveEvent<Void?>()
    val logoutClickEvent: LiveData<Void?> = _logoutClickEvent
    private val _logoutFinishEvent = SingleLiveEvent<Void?>()
    val logoutFinishEvent: LiveData<Void?> = _logoutFinishEvent

    fun logoutClick(){
        _logoutClickEvent.value = null
    }

    fun logout(){
        UserApiClient.instance.logout {error ->
            if (error != null)
                Log.e("Kakao logout"," logout failed", error)
            else
                Log.i("Kakao logout", "logout success")
        }

        Firebase.auth.signOut()

        mSharedPreferences!!.edit().clear().apply()

        _toastMessage.value = "Logout Success"
        _logoutFinishEvent.value = null
    }

    private val _withdrawalClickEvent = SingleLiveEvent<Void?>()
    val withdrawalClickEvent: LiveData<Void?> = _withdrawalClickEvent

    fun withdrawalClickEvent(){
        _withdrawalClickEvent.value = null
    }

}