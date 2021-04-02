package com.tomasandfriends.bansikee.src.activities.login

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.NETWORK_ERROR
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.login.interfaces.LoginView
import com.tomasandfriends.bansikee.src.activities.login.models.LoginBody
import com.tomasandfriends.bansikee.src.activities.login.models.LoginData

class LoginViewModel : BaseViewModel(), LoginView {

    private val _splash = MutableLiveData<Boolean>()
    val splash : LiveData<Boolean> get() = _splash

    val email = MutableLiveData<String?>()

    private val _checkEmail = MutableLiveData<Int>()
    val checkEmail : LiveData<Int> get() = _checkEmail

    val password = MutableLiveData<String>()

    private val _checkPassword = MutableLiveData<Int>()
    val checkPassword : LiveData<Int> get() = _checkPassword

    private val _goSignUpEvent = SingleLiveEvent<Void?>()
    val goSignUpEvent : LiveData<Void?> get() = _goSignUpEvent

    private val _kakaoLoginEvent = SingleLiveEvent<Void?>()
    val kakaoLoginEvent : LiveData<Void?> get() = _kakaoLoginEvent

    private val _googleLoginEvent = SingleLiveEvent<Void?>()
    val googleLoginEvent : LiveData<Void?> get() = _googleLoginEvent

    private val _goMainActivityEvent = SingleLiveEvent<Void?>()
    val goMainActivityEvent : LiveData<Void?> get() = _goMainActivityEvent

    private val loginService = LoginService(this)

    init {
        _splash.value = true

        //auto login after splash
        Handler(Looper.myLooper()!!).postDelayed({ autoLogin() }, 2000)
    }

    //auto login
    private fun autoLogin() {
        loginService.autoLogin()
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

    //base login
    fun inAppLogin() {
        _clearInput.value = null

        val strEmail = email.value
        val strPw = password.value

        _checkEmail.value = if(strEmail.isNullOrEmpty()) R.string.pls_write_email else null
        _checkPassword.value = if(strPw.isNullOrEmpty()) R.string.pls_write_password else null

        if (!strEmail.isNullOrEmpty() && !strPw.isNullOrEmpty())
            loginService.basicLogin(LoginBody(strEmail, strPw))
    }

    //go sign up
    fun goSignUp() {
        _goSignUpEvent.value = null
    }

    override fun autoLoginSuccess() {
        loginAnonymouslyOnFirebase()

        _goMainActivityEvent.value = null
    }

    override fun autoLoginFailed() {
        _splash.value = false
    }

    //when login api success
    override fun loginSuccess(loginData: LoginData) {

        val editor = ApplicationClass.mSharedPreferences!!.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, loginData.jwt)
        editor.putString(ApplicationClass.USER_EMAIL, loginData.email)
        editor.putString(ApplicationClass.USER_NAME, loginData.name)
        editor.apply()

        loginAnonymouslyOnFirebase()

        _goMainActivityEvent.value = null;
    }

    private fun loginAnonymouslyOnFirebase() {
        val auth = Firebase.auth
        val user = auth.currentUser
        if (user == null ){
            auth.signInAnonymously()
        }
    }

    //when login api failed
    override fun loginFailed(msg: String?) {
        _snackbarMessage.value = msg ?: NETWORK_ERROR
    }
}