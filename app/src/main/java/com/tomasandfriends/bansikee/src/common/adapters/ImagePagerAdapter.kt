package com.tomasandfriends.bansikee.src.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.tomasandfriends.bansikee.databinding.ItemDiaryHorizontalBinding
import com.tomasandfriends.bansikee.databinding.ItemImgPagerBinding

class ImagePagerAdapter(context: Context): RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    private val mContext = context
    private val mImgDataList = ArrayList<String>()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        inflater = LayoutInflater.from(mContext)
        val itemBinding = ItemImgPagerBinding.inflate(inflater, parent, false)

        return ImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imgUrl = mImgDataList[position]
        holder.bind(imgUrl)
    }

    override fun getItemCount(): Int {
        return mImgDataList.size
    }

    fun updateItems(items: List<String>){
        mImgDataList.clear()
        mImgDataList.addAll(items)
        notifyDataSetChanged()
    }

    class ImageViewHolder(binding: ItemImgPagerBinding)
        : RecyclerView.ViewHolder(binding.root){
        private val mBinding = binding

        fun bind(imgUrl: String){
            mBinding.imgUrl = imgUrl
        }
    }
}