package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tomasandfriends.bansikee.databinding.ItemPlantBinding

class PlantAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mDataViewModels = ArrayList<PlantItemViewModel>()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        inflater = LayoutInflater.from(mContext)

        val itemBinding = ItemPlantBinding.inflate(inflater, parent, false)
        return PlantViewHolder(mContext, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemViewModel = mDataViewModels[position]

        if (holder is PlantViewHolder) {
            holder.bind(itemViewModel)
        }
    }

    override fun getItemCount(): Int {
        return mDataViewModels.size
    }

    fun updateItems(items: List<PlantItemViewModel>){
        mDataViewModels.clear()
        mDataViewModels.addAll(items)
        notifyDataSetChanged()
    }

    class PlantViewHolder(context: Context, binding: ItemPlantBinding)
        : RecyclerView.ViewHolder(binding.root){
        private val mContext = context
        private val mBinding = binding

        fun bind(viewModel: PlantItemViewModel){
            mBinding.viewModel = viewModel
            mBinding.lifecycleOwner = mContext as LifecycleOwner

            viewModel.goDetailsEvent.observe(mContext as LifecycleOwner, {
                //go plant details activity
            })
        }
    }
}