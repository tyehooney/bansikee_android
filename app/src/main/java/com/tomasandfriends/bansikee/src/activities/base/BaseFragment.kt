package com.tomasandfriends.bansikee.src.activities.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>: Fragment() {
    protected lateinit var binding : B
    protected lateinit var viewModel : VM

    abstract fun getLayoutId(): Int
    abstract fun setViewModel()

    private fun showToast(msg : String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(),
                container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setViewModel()

        setBaseEvents()
    }

    private fun setBaseEvents(){
        viewModel.snackbarMessage.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, {
            showToast(it)
        })

        viewModel.back.observe(viewLifecycleOwner, {
            requireActivity().onBackPressed()
        })

        viewModel.finishEvent.observe(viewLifecycleOwner, {
            requireActivity().finish()
        })

        viewModel.clearInput.observe(viewLifecycleOwner, {
            val imm = requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isAcceptingText && requireActivity().currentFocus != null){
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshUserInfo()
    }
}