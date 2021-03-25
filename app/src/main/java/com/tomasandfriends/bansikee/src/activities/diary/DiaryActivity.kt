package com.tomasandfriends.bansikee.src.activities.diary

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityDiaryBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class DiaryActivity: BaseActivity<ActivityDiaryBinding, DiaryViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_diary
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(DiaryViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.getBundleExtra("bundle") == null){
            viewModel.getDiaryData(intent.getIntExtra("diaryIdx", 0))
        }else{
            viewModel.initWriteDiary(intent.getBundleExtra("bundle")!!)
            binding.root.isEnabled = false
        }
    }
}