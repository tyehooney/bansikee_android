package com.tomasandfriends.bansikee.src.activities.main.fragment_my_garden

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentMyGardenBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment

class MyGardenFragment: BaseFragment<FragmentMyGardenBinding, MyGardenViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_garden
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(MyGardenViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.searchingWord.observe(viewLifecycleOwner, {
            viewModel.search(it)
        })

        viewModel.deleteMyPlantClickEvent.observe(viewLifecycleOwner, {
            AlertDialog.Builder(requireContext())
                    .setMessage(R.string.alert_delete_my_plant)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        viewModel.deleteMyPlant(it)
                    }.setNegativeButton(R.string.no, null)
                    .create().show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyPlants()
    }
}