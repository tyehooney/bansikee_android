package com.tomasandfriends.bansikee.src.activities.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.ApplicationClass.Companion.NEW_NOTI
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
    protected val _newNoti = MutableLiveData(mSharedPreferences!!.getBoolean(NEW_NOTI, false))
    val newNoti: LiveData<Boolean> = _newNoti

    protected val _userEmail = MutableLiveData(mSharedPreferences!!.getString(USER_EMAIL, ""))
    val userEmail: LiveData<String?> = _userEmail
    protected val _userName = MutableLiveData(mSharedPreferences!!.getString(USER_NAME, ""))
    val userName: LiveData<String?> = _userName
    protected val _userImg = MutableLiveData(mSharedPreferences!!.getString(USER_IMG, ""))
    val userImg: LiveData<String?> = _userImg

    fun setLoading(b : Boolean){
        _loading.value = b
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

    fun refreshUserInfo(){
        _userEmail.value = mSharedPreferences!!.getString(USER_EMAIL, "")
        _userName.value = mSharedPreferences!!.getString(USER_NAME, "")
        _userImg.value = mSharedPreferences!!.getString(USER_IMG, "")
        _newNoti.value = mSharedPreferences!!.getBoolean(NEW_NOTI, false)
    }
}