package com.tomasandfriends.bansikee.src.activities.plant_details

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityPlantDetailsBinding
import com.tomasandfriends.bansikee.src.activities.add_my_plant.AddMyPlantActivity
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class PlantDetailsActivity : BaseActivity<ActivityPlantDetailsBinding, PlantDetailsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_plant_details
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PlantDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goAddMyPlantEvent.observe(this, {
            val intent = Intent(this, AddMyPlantActivity::class.java)
            intent.putExtras(it)
            startActivity(intent)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getPlantDetails(intent.getIntExtra("plantIdx", 0),
                intent.getStringExtra("status")!!)
    }

}