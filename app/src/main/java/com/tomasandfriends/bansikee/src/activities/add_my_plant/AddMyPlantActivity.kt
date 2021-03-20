package com.tomasandfriends.bansikee.src.activities.add_my_plant

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityAddMyPlantBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class AddMyPlantActivity : BaseActivity<ActivityAddMyPlantBinding, AddMyPlantViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_add_my_plant
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(AddMyPlantViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getPhotoEvent.observe(this, {
            //get photo from gallery or camera
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val plantIdx = intent.getIntExtra("plantIdx", 0)
        val plantName = intent.getStringExtra("plantName")
        val plantSpecies = intent.getStringExtra("plantSpecies")
        viewModel.getPlantData(plantIdx, plantName!!, plantSpecies!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}