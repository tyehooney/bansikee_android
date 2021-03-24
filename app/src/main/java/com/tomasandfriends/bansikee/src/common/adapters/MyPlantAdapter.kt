package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tomasandfriends.bansikee.databinding.ItemMyPlantBinding

class MyPlantAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mDataViewModels = ArrayList<MyPlantItemViewModel>()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        inflater = LayoutInflater.from(mContext)

        val itemBinding = ItemMyPlantBinding.inflate(inflater, parent, false)

        return MyPlantViewHolder(mContext, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemViewModel = mDataViewModels[position]

        if (holder is MyPlantViewHolder) {
            holder.bind(itemViewModel)
        }

        itemViewModel.goDetailsEvent.observe(mContext as LifecycleOwner, {
            //TODO : go MyPlantDetailsActivity
        })
    }

    override fun getItemCount(): Int {
        return mDataViewModels.size
    }

    fun updateItems(items: List<MyPlantItemViewModel>){
        mDataViewModels.clear()
        mDataViewModels.addAll(items)
        notifyDataSetChanged()
    }

    class MyPlantViewHolder(context: Context, binding: ItemMyPlantBinding)
        : RecyclerView.ViewHolder(binding.root){
        private val mContext = context
        private val mBinding = binding

        fun bind(viewModel: MyPlantItemViewModel){
            mBinding.viewModel = viewModel
            mBinding.lifecycleOwner = mContext as LifecycleOwner
        }
    }
}