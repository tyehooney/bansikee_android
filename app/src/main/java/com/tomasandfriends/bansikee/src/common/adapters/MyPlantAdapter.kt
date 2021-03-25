package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tomasandfriends.bansikee.databinding.ItemMyPlantBinding
import com.tomasandfriends.bansikee.src.activities.my_plant_details.MyPlantDetailsActivity

class MyPlantAdapter(context: Context): RecyclerView.Adapter<MyPlantAdapter.MyPlantViewHolder>() {

    private val mContext = context
    private val mDataViewModels = ArrayList<MyPlantItemViewModel>()
    private lateinit var inflater: LayoutInflater

    lateinit var deleteMyPlantListener: DeleteMyPlantListener

    interface DeleteMyPlantListener {
        fun onDeleteClick(myPlantIdx: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlantViewHolder {

        inflater = LayoutInflater.from(mContext)

        val itemBinding = ItemMyPlantBinding.inflate(inflater, parent, false)

        return MyPlantViewHolder(mContext, itemBinding)
    }

    override fun onBindViewHolder(holder: MyPlantViewHolder, position: Int) {

        val itemViewModel = mDataViewModels[position]

        holder.bind(itemViewModel)

        holder.itemView.setOnLongClickListener {
            for (i in 0 until mDataViewModels.size){
                if (i != position) mDataViewModels[i].setDeleteShowing(false)
            }

            itemViewModel.setDeleteShowing(true)

            true
        }

        itemViewModel.deleteEvent.observe(mContext as LifecycleOwner, {
            deleteMyPlantListener.onDeleteClick(it)
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

            viewModel.goDetailsEvent.observe(mContext as LifecycleOwner, {
                if(viewModel.deleteShowing.value!!) viewModel.setDeleteShowing(false)
                else{
                    val intent = Intent(mContext, MyPlantDetailsActivity::class.java)
                    intent.putExtra("myPlantIdx", it)
                    mContext.startActivity(intent)
                }
            })
        }
    }
}