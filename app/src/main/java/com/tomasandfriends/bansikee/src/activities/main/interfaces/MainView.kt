package com.tomasandfriends.bansikee.src.activities.main.interfaces

interface MainView {
    fun isOnboardedSuccess(onboarded: Boolean)
    fun isOnboardedFailed(msg: String?)
}