package com.tomasandfriends.bansikee.src.activities.liking_plants

import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityLikingPlantsBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class LikingPlantsActivity : BaseActivity<ActivityLikingPlantsBinding, LikingPlantsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_liking_plants
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(LikingPlantsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLikingPlants()
    }
}