package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentSearchResultBinding

class SearchResultFragment: Fragment() {
    private lateinit var viewModel: EncyclopediaViewModel
    private lateinit var binding: FragmentSearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
            .get(EncyclopediaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result,
            container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.searchPlants()
    }
}