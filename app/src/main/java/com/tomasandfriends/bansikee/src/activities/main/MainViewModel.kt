package com.tomasandfriends.bansikee.src.activities.main

import androidx.lifecycle.LiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MainView

class MainViewModel : BaseViewModel(), MainView {

    private val _goOnboardingEvent = SingleLiveEvent<Void?>()
    val goOnboardingEvent: LiveData<Void?> = _goOnboardingEvent

    private val mainService = MainService(this)

    init{
//        mainService.isOnboarded()
//        _goOnboardingEvent.value = null
    }

    override fun isOnboardedSuccess(onboarded: Boolean) {
        if(!onboarded)
            _goOnboardingEvent.value = null
    }

    override fun isOnboardedFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

}