package com.tomasandfriends.bansikee.src.utils

import android.app.AlertDialog
import android.content.Context
import com.tomasandfriends.bansikee.R

object SystemUtils {
    fun showAlert(context: Context, titleResId: Int, messageResId: Int){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titleResId)
            .setMessage(messageResId)
            .setPositiveButton(R.string.check, null)
            .create().show()
    }

    fun getDayOfWeek(intDayOfWeek: Int): String{
        return when(intDayOfWeek) {
            0 -> "토요일"
            1 -> "일요일"
            2 -> "월요일"
            3 -> "화요일"
            4 -> "수요일"
            5 -> "목요일"
            6 -> "금요일"
            else -> "error"
        }
    }
}