package com.thomasandfriends.bansikee.src.activities.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomasandfriends.bansikee.src.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val snackbarMessage = SingleLiveEvent<String>()
    val toastMessage = SingleLiveEvent<String>()
    val back = SingleLiveEvent<Void>()
    val finishEvent = SingleLiveEvent<Void>()

    fun setLoading(b : Boolean){
        loading.value = b
    }

    fun setSnackbarMessage(str : String){
        snackbarMessage.value = str
    }

    fun setToastMessage(str : String){
        toastMessage.value = str
    }

    fun goBack(){
        back.value = null
    }

    fun finish(){
        finishEvent.value = null
    }
}