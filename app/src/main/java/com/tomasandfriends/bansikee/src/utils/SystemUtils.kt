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
}