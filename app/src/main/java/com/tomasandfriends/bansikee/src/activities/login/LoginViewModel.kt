package com.tomasandfriends.bansikee.src.activities.login

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel

class LoginViewModel : BaseViewModel() {

    private val _splash = MutableLiveData<Boolean>()
    val splash : LiveData<Boolean> get() = _splash

    val email = MutableLiveData<String>()

    private val _checkEmail = MutableLiveData<Int>()
    val checkEmail : LiveData<Int> get() = _checkEmail

    val password = MutableLiveData<String>()

    private val _checkPassword = MutableLiveData<Int>()
    val checkPassword : LiveData<Int> get() = _checkPassword

    private val _goSignUpEvent = SingleLiveEvent<Void>()
    val goSignUpEvent : LiveData<Void> get() = _goSignUpEvent

    private val _kakaoLoginEvent = SingleLiveEvent<Void>()
    val kakaoLoginEvent : LiveData<Void> get() = _kakaoLoginEvent

    private val _googleLoginEvent = SingleLiveEvent<Void>()
    val googleLoginEvent : LiveData<Void> get() = _googleLoginEvent

    init {
        _splash.value = true

        //auto login after splash
        Handler(Looper.myLooper()!!).postDelayed({ autoLogin() }, 2000)
    }

    //auto login
    private fun autoLogin() {
        _splash.value = false    //temp
    }

    //kakao login
    fun kakaoLoginClick() {
        _kakaoLoginEvent.value = null
    }

    //google login
    fun googleLoginClick() {
        _googleLoginEvent.value = null
    }

    //go sign up
    fun goSignUp() {
        _goSignUpEvent.value = null
    }

    fun inAppLogin() {}
}