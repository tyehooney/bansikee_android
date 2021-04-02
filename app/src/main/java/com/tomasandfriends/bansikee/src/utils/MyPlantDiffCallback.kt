package com.tomasandfriends.bansikee.src.utils

import androidx.recyclerview.widget.DiffUtil
import com.tomasandfriends.bansikee.src.common.adapters.MyPlantItemViewModel

class MyPlantDiffCallback(private val oldPlants: List<MyPlantItemViewModel>,
                          private val newPlants: List<MyPlantItemViewModel>): DiffUtil.Callback() {

    override fun getOldListSize() = oldPlants.size

    override fun getNewListSize() = newPlants.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPlants[oldItemPosition].myPlantIdx == newPlants[newItemPosition].myPlantIdx

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPlants[oldItemPosition] == newPlants[newItemPosition]
}