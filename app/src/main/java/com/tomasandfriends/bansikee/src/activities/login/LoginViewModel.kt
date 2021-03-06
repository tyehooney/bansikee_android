package com.tomasandfriends.bansikee.src.activities.login

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.sdk.auth.model.OAuthToken
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.login.interfaces.LoginView

class LoginViewModel : BaseViewModel(), LoginView {

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

    private val loginService = LoginService(this)

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

    fun kakaoLogin(accessToken : String){
        loginService.kakaoLogin(accessToken)
    }

    //google login
    fun googleLoginClick() {
        _googleLoginEvent.value = null
    }

    fun googleLogin(idToken : String){
        loginService.googleLogin(idToken)
    }

    //go sign up
    fun goSignUp() {
        _goSignUpEvent.value = null
    }

    fun inAppLogin() {}

    override fun socialLoginSuccess() {
        snackbarMessage.value = "google login : Success!"
    }

    override fun socialLoginFailed(msg: String?) {
        snackbarMessage.value = msg ?: "네트워크 에러"
    }
}