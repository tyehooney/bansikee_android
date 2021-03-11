package com.tomasandfriends.bansikee.src.activities.sign_up.interfaces

interface SignUpView {

    fun checkNicknameSuccess(duplicated: Boolean)
    fun checkNicknameFailed(msg: String?)

    fun signUpSuccess(msg: String)
    fun signUpFailed(msg : String?)
}