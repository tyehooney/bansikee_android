package com.tomasandfriends.bansikee.src.activities.plant_details

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityPlantDetailsBinding
import com.tomasandfriends.bansikee.databinding.ActivitySignUpBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class PlantDetailsActivity : BaseActivity<ActivityPlantDetailsBinding, PlantDetailsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_plant_details
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PlantDetailsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}