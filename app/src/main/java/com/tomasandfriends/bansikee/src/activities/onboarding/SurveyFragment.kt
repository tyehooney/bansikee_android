package com.tomasandfriends.bansikee.src.activities.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentSurveyBinding

class SurveyFragment: Fragment() {
    private val viewModel: OnboardingViewModel by activityViewModels()
    private lateinit var binding: FragmentSurveyBinding
    private var pageIndex: Int = 0

    companion object {
        fun newInstance(pageIndex: Int): SurveyFragment{
            val args = Bundle()
            args.putInt("pageIndex", pageIndex)

            val fragment = SurveyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pageIndex = requireArguments().getInt("pageIndex")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey,
            container, false)
        binding.viewModel = viewModel
        binding.pageIndex = pageIndex

        return binding.root
    }
}