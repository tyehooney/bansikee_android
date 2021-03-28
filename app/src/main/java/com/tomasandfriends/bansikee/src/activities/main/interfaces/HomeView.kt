package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.activities.main.models.HomeData

interface HomeView {

    fun getHomeDataSuccess(homeData: HomeData)
    fun getHomeDataFailed(msg: String?)

}