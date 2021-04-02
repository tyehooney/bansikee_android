package com.tomasandfriends.bansikee.src.utils

import androidx.recyclerview.widget.DiffUtil
import com.tomasandfriends.bansikee.src.common.adapters.PlantItemViewModel

class PlantDiffCallback(private val oldPlants: List<PlantItemViewModel>,
                        private val newPlants: List<PlantItemViewModel>): DiffUtil.Callback() {

    override fun getOldListSize() = oldPlants.size

    override fun getNewListSize() = newPlants.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPlants[oldItemPosition].plantIdx == newPlants[newItemPosition].plantIdx

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPlants[oldItemPosition] == newPlants[newItemPosition]
}