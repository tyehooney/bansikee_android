package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tomasandfriends.bansikee.databinding.ItemHomeMyPlantBinding
import com.tomasandfriends.bansikee.src.activities.my_plant_details.MyPlantDetailsActivity

class HomeMyPlantAdapter(context: Context): RecyclerView.Adapter<HomeMyPlantAdapter.HomeMyPlantViewHolder>() {

    private val mContext = context
    private val mDataViewModels = ArrayList<HomeMyPlantItemViewModel>()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMyPlantViewHolder {
        inflater = LayoutInflater.from(mContext)

        val itemHomeMyPlantBinding = ItemHomeMyPlantBinding.inflate(inflater, parent, false)

        return HomeMyPlantViewHolder(mContext, itemHomeMyPlantBinding)
    }

    override fun onBindViewHolder(holder: HomeMyPlantViewHolder, position: Int) {
        val itemViewModel = mDataViewModels[position]

        holder.bind(itemViewModel)
    }

    override fun getItemCount(): Int {
        return mDataViewModels.size
    }

    fun updateItems(items: List<HomeMyPlantItemViewModel>){
        mDataViewModels.clear()
        mDataViewModels.addAll(items)
        notifyDataSetChanged()
    }

    class HomeMyPlantViewHolder(context: Context, binding: ItemHomeMyPlantBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private val mContext = context
        private val mBinding = binding

        fun bind(viewModel: HomeMyPlantItemViewModel){
            mBinding.viewModel = viewModel
            mBinding.lifecycleOwner = mContext as LifecycleOwner

            viewModel.itemClickEvent.observe(mContext as LifecycleOwner, {
                val intent = Intent(mContext, MyPlantDetailsActivity::class.java)
                intent.putExtra("myPlantIdx", it)
                mContext.startActivity(intent)
            })
        }
    }
}