package com.tomasandfriends.bansikee.src.common.interfaces

interface CheckNicknameView {

    fun checkNickname(strNickname: String)
    fun checkNicknameSuccess(duplicated: Boolean)
    fun checkNicknameFailed(msg: String?)

}