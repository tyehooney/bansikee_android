package com.tomasandfriends.bansikee.src.activities.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var binding : B
    protected lateinit var viewModel : VM

    abstract fun getLayoutId(): Int
    abstract fun setViewModel()

    private fun showToast(msg : String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        setViewModel()

        setBaseEvents()
    }

    private fun setBaseEvents(){
        viewModel.snackbarMessage.observe(this, Observer<String> {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.toastMessage.observe(this, Observer<String> {
            showToast(it)
        })

        viewModel.back.observe(this, Observer {
            onBackPressed()
        })

        viewModel.finishEvent.observe(this, Observer {
            finish()
        })
    }
}