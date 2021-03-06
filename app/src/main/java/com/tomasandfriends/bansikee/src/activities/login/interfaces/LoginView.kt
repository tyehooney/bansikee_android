package com.tomasandfriends.bansikee.src.activities.login.interfaces

interface LoginView {
    fun loginSuccess()
    fun loginFailed(msg : String?)
}