package com.tomasandfriends.bansikee.src.activities.login.interfaces

interface LoginView {
    fun socialLoginSuccess()
    fun socialLoginFailed(msg : String?)
}