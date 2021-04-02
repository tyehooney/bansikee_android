package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import androidx.lifecycle.LiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MyPageView

class MyPageViewModel : BaseViewModel(), MyPageView {

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

    fun logoutClick(){
        _logoutClickEvent.value = null
    }

    private val _withdrawalClickEvent = SingleLiveEvent<Void?>()
    val withdrawalClickEvent: LiveData<Void?> = _withdrawalClickEvent

    private val _withdrawalFinishEvent = SingleLiveEvent<Void?>()
    val withdrawalFinishEvent: LiveData<Void?> = _withdrawalFinishEvent

    private val myPageService = MyPageService(this)

    fun withdrawalClickEvent(){
        _withdrawalClickEvent.value = null
    }

    fun withdrawal(){
        myPageService.withdrawal()
    }

    override fun withdrawalSuccess(msg: String) {
        _toastMessage.value = msg
        _withdrawalFinishEvent.value = null
    }

    override fun withdrawalFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}