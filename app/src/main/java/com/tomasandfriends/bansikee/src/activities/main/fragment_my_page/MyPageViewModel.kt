package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import androidx.lifecycle.LiveData
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

}