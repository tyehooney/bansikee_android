package com.tomasandfriends.bansikee.src.utils

import androidx.recyclerview.widget.DiffUtil
import com.tomasandfriends.bansikee.src.common.adapters.DiaryItemViewModel

class DiaryDiffCallback(private val oldPlants: List<DiaryItemViewModel>,
                        private val newPlants: List<DiaryItemViewModel>): DiffUtil.Callback() {

    override fun getOldListSize() = oldPlants.size

    override fun getNewListSize() = newPlants.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPlants[oldItemPosition].diaryIdx == newPlants[newItemPosition].diaryIdx

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPlants[oldItemPosition] == newPlants[newItemPosition]
}