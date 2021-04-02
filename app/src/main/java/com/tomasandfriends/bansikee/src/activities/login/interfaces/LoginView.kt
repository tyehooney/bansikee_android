package com.tomasandfriends.bansikee.src.activities.login.interfaces

import com.tomasandfriends.bansikee.src.activities.login.models.LoginData

interface LoginView {
    fun autoLoginSuccess()
    fun autoLoginFailed()

    fun loginSuccess(loginData: LoginData)
    fun loginFailed(msg : String?)
}