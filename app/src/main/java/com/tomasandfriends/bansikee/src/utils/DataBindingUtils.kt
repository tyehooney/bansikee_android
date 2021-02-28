package com.tomasandfriends.bansikee.src.utils

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.tomasandfriends.bansikee.R

object DataBindingUtils {

    //add fade in/out animation
    @BindingAdapter("fadeVisibility")
    @JvmStatic
    fun setFadeVisibility(view : View, visible : Boolean){
        val animId =
            if (visible) R.anim.fade_in
            else R.anim.fade_out

        val animFade = AnimationUtils.loadAnimation(view.context, animId)
        view.animation = animFade
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    //control editText background
    @BindingAdapter("backgroundReactive")
    @JvmStatic
    fun setBackgroundReactive(textInputLayout: TextInputLayout, errMsg : Int?){
        val editText = textInputLayout.editText
        val context = textInputLayout.context

        editText!!.setOnFocusChangeListener { v, hasFocus ->
            v.background =
                    if (errMsg != null && errMsg != 0) {
                        textInputLayout.error = context.getString(errMsg)
                        ContextCompat.getDrawable(context, R.drawable.edittext_background_error)
                    } else {
                        if (hasFocus)
                            ContextCompat.getDrawable(context, R.drawable.edittext_background_focus)
                        else
                            ContextCompat.getDrawable(context, R.drawable.edittext_background)
                    }
        }

        if (errMsg != null && errMsg != 0) {
            textInputLayout.error = context.getString(errMsg)
            editText.background = ContextCompat.getDrawable(context, R.drawable.edittext_background_error)
        } else {
            textInputLayout.error = null
            editText.background =
                    if (editText.isFocused)
                        ContextCompat.getDrawable(context, R.drawable.edittext_background_focus)
                    else
                        ContextCompat.getDrawable(context, R.drawable.edittext_background)
        }
    }
}