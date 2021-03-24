package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tomasandfriends.bansikee.databinding.ItemDiaryBinding
import com.tomasandfriends.bansikee.databinding.ItemDiaryHorizontalBinding
import com.tomasandfriends.bansikee.databinding.ItemPlantBinding
import com.tomasandfriends.bansikee.databinding.ItemPlantHorizontalBinding
import com.tomasandfriends.bansikee.src.activities.plant_details.PlantDetailsActivity

class DiaryAdapter(context: Context, horizontal: Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mHorizontal = horizontal
    private val mDataViewModels = ArrayList<DiaryItemViewModel>()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        inflater = LayoutInflater.from(mContext)

        val itemBinding =
                if(mHorizontal)
                    ItemPlantHorizontalBinding.inflate(inflater, parent, false)
                else
                    ItemPlantBinding.inflate(inflater, parent, false)

        return if (mHorizontal)
            DiaryHorizontalViewHolder(mContext, itemBinding as ItemDiaryHorizontalBinding)
        else
            DiaryViewHolder(mContext, itemBinding as ItemDiaryBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemViewModel = mDataViewModels[position]

        if (holder is DiaryViewHolder) {
            holder.bind(itemViewModel)
        } else if(holder is DiaryHorizontalViewHolder){
            holder.bind(itemViewModel)
        }

        itemViewModel.goDetailsEvent.observe(mContext as LifecycleOwner, {
        })
    }

    override fun getItemCount(): Int {
        return mDataViewModels.size
    }

    fun updateItems(items: List<DiaryItemViewModel>){
        mDataViewModels.clear()
        mDataViewModels.addAll(items)
        notifyDataSetChanged()
    }

    class DiaryViewHolder(context: Context, binding: ItemDiaryBinding)
        : RecyclerView.ViewHolder(binding.root){
        private val mContext = context
        private val mBinding = binding

        fun bind(viewModel: DiaryItemViewModel){
            mBinding.viewModel = viewModel
            mBinding.lifecycleOwner = mContext as LifecycleOwner
        }
    }

    class DiaryHorizontalViewHolder(context: Context, binding: ItemDiaryHorizontalBinding)
        : RecyclerView.ViewHolder(binding.root){
        private val mContext = context
        private val mBinding = binding

        fun bind(viewModel: DiaryItemViewModel){
            mBinding.viewModel = viewModel
            mBinding.lifecycleOwner = mContext as LifecycleOwner
        }
    }
}