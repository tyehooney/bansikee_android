package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tomasandfriends.bansikee.databinding.ItemMoreBinding
import com.tomasandfriends.bansikee.databinding.ItemPlantBinding
import com.tomasandfriends.bansikee.databinding.ItemPlantHorizontalBinding
import com.tomasandfriends.bansikee.src.activities.plant_details.PlantDetailsActivity

class PagingPlantAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_FOOTER = 0
    }

    private val mContext = context
    private val mDataViewModels = ArrayList<PlantItemViewModel>()
    private var lastPage = false
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        inflater = LayoutInflater.from(mContext)

        var holder: RecyclerView.ViewHolder? = null

        if (viewType == VIEW_TYPE_FOOTER) {
            val itemMoreBinding = ItemMoreBinding.inflate(inflater, parent, false)
            holder = MoreItemsViewHolder(mContext, itemMoreBinding)
        } else if(viewType == VIEW_TYPE_ITEM) {
            val itemBinding = ItemPlantBinding.inflate(inflater, parent, false)
            holder = PlantAdapter.PlantViewHolder(mContext, itemBinding)
        }

        return holder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PlantAdapter.PlantViewHolder) {
            val itemViewModel = mDataViewModels[position]
            holder.bind(itemViewModel)
            holder.mBinding.ivBtnLike.isEnabled = false
        } else if (holder is MoreItemsViewHolder) {
            holder.bind()
            if (lastPage || itemCount % 10 != 1) holder.mBinding.root.visibility = View.GONE
            else holder.mBinding.root.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mDataViewModels.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_FOOTER else VIEW_TYPE_ITEM
    }

    fun updateItems(items: List<PlantItemViewModel>){
        mDataViewModels.clear()
        mDataViewModels.addAll(items)
        notifyDataSetChanged()
    }

    fun setLastPage(bool: Boolean){
        lastPage = bool
        notifyDataSetChanged()
    }

    class MoreItemsViewHolder(context: Context, binding: ItemMoreBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private val mContext = context
        val mBinding = binding

        fun bind(){ mBinding.lifecycleOwner = mContext as LifecycleOwner }
    }
}