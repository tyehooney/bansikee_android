package com.tomasandfriends.bansikee.src.activities.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityMainBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}