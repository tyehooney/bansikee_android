package com.tomasandfriends.bansikee.src.activities.main.fragment_my_garden

import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel

class MyGardenViewModel : BaseViewModel() {

    val searchingWord = MutableLiveData<String>()

    init{
    }

}