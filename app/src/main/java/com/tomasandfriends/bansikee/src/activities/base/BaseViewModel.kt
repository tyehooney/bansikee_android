package com.tomasandfriends.bansikee.src.activities.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_EMAIL
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_IMG
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_NAME
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    protected val _snackbarMessage = SingleLiveEvent<String>()
    val snackbarMessage: LiveData<String> get() = _snackbarMessage
    protected val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    protected val _back = SingleLiveEvent<Void?>()
    val back: LiveData<Void?> get() = _back
    protected val _finishEvent = SingleLiveEvent<Void?>()
    val finishEvent: LiveData<Void?> get() = _finishEvent
    protected val _clearInput = SingleLiveEvent<Void?>()
    val clearInput: LiveData<Void?> get() = _clearInput

    val userEmail = mSharedPreferences!!.getString(USER_EMAIL, "")
    val userName = mSharedPreferences!!.getString(USER_NAME, "")
    val userImg = mSharedPreferences!!.getString(USER_IMG, "")

    fun setLoading(b : Boolean){
        _loading.value = b
    }

    fun setSnackbarMessage(str : String){
        _snackbarMessage.value = str
    }

    fun setToastMessage(str : String){
        _toastMessage.value = str
    }

    fun clearInput(){
        _clearInput.value = null
    }

    fun goBack(){
        _back.value = null
    }

    fun finish(){
        _finishEvent.value = null
    }
}