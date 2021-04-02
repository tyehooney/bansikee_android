package com.tomasandfriends.bansikee.src.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityMainBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity
import com.tomasandfriends.bansikee.src.activities.onboarding.OnboardingActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var navController: NavController
    private var lastBackPressedTime: Long = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goOnboardingEvent.observe(this, {
            startActivity(Intent(this, OnboardingActivity::class.java))
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = supportFragmentManager.findFragmentById(R.id.fragment_nav_host)!!.findNavController()
        navController.let {
            binding.bnvMenu.setupWithNavController(navController)
        }
    }

    override fun onBackPressed() {
        if(navController.graph.startDestination == navController.currentDestination?.id) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressedTime < 2000)
                finish()
            else{
                lastBackPressedTime = currentTime
                viewModel.setToast(getString(R.string.on_back_pressed))
            }
        } else {
            super.onBackPressed()
        }
    }
}