package com.tomasandfriends.bansikee.src.activities.my_plant_details

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityMyPlantDetailsBinding
import com.tomasandfriends.bansikee.src.activities.add_my_plant.AddMyPlantActivity
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity
import com.tomasandfriends.bansikee.src.activities.plant_details.PlantDetailsActivity

class MyPlantDetailsActivity: BaseActivity<ActivityMyPlantDetailsBinding, MyPlantDetailsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_my_plant_details
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(MyPlantDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goPlantDetailsEvent.observe(this, {
            val intent = Intent(this, PlantDetailsActivity::class.java)
            intent.putExtra("plantIdx", it)
            intent.putExtra("status", "")
            startActivity(intent)
        })

        viewModel.goEditMyPlantEvent.observe(this, {
            val intent = Intent(this, AddMyPlantActivity::class.java)
            intent.putExtra("bundle", it)
            startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyPlantDetails(intent.getIntExtra("myPlantIdx", 0))
    }
}