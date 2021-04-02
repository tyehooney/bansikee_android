package com.tomasandfriends.bansikee.src.activities.sign_up

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivitySignUpBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_sign_up
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}