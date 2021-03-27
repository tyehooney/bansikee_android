package com.tomasandfriends.bansikee.src.activities.my_plant_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentDiaryListBinding

class DiaryListFragment: Fragment() {
    private val viewModel: MyPlantDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentDiaryListBinding

    companion object {
        fun newInstance(myPlantIdx: Int): DiaryListFragment{
            val args = Bundle()
            args.putInt("myPlantIdx", myPlantIdx)

            val fragment = DiaryListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_list,
                container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()

        return binding.root
    }
}