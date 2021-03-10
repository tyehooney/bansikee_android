package com.tomasandfriends.bansikee.src.activities.login.interfaces

interface LoginView {
    fun autoLoginSuccess()
    fun autoLoginFailed()

    fun loginSuccess()
    fun loginFailed(msg : String?)
}