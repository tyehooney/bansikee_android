package com.tomasandfriends.bansikee.src.common.interfaces

import com.tomasandfriends.bansikee.src.common.models.PlantData

interface RecommendationView {
    fun getRecommendationsSuccess(recommendations: List<PlantData>)
    fun getRecommendationsFailed(msg : String?)
}