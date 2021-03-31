package com.tomasandfriends.bansikee.src.utils

import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.view.Gravity.CENTER
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia.EncyclopediaViewModel
import com.tomasandfriends.bansikee.src.activities.main.fragment_my_garden.MyGardenViewModel
import com.tomasandfriends.bansikee.src.activities.my_plant_details.MyPlantDetailsViewModel
import com.tomasandfriends.bansikee.src.activities.onboarding.OnboardingViewModel
import com.tomasandfriends.bansikee.src.activities.onboarding.models.SurveyData
import com.tomasandfriends.bansikee.src.activities.sign_up.SignUpViewModel
import com.tomasandfriends.bansikee.src.common.adapters.*
import com.tomasandfriends.bansikee.src.common.interfaces.CheckNicknameView
import com.tomasandfriends.bansikee.src.utils.SystemUtils.convertDpToPx
import kotlin.math.roundToInt

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

    @BindingAdapter("nicknameDuplicated", "resultMessage" ,"checkNicknameListener")
    @JvmStatic
    fun setCheckNickname(textInputLayout: TextInputLayout, duplicated: Boolean,
                         msg: Int?, listener: CheckNicknameView){
        val editText = textInputLayout.editText
        val context = textInputLayout.context

        editText!!.setOnFocusChangeListener{ v, hasFocus ->
            v.background =
                    if (msg != null && duplicated) {
                        textInputLayout.error = context.getString(msg)
                        ContextCompat.getDrawable(context, R.drawable.edittext_background_error)
                    } else {
                        if (hasFocus)
                            ContextCompat.getDrawable(context, R.drawable.edittext_background_focus)
                        else
                            ContextCompat.getDrawable(context, R.drawable.edittext_background)
                    }

            if(!hasFocus)
                listener.checkNickname(editText.text.toString())
        }

        editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                listener.checkNickname(editText.text.toString())
            }
            false
        }

        if (msg != null && duplicated && editText.text.isNotEmpty()){
            textInputLayout.helperText = null
            textInputLayout.error = context.getText(msg)
            editText.background = ContextCompat.getDrawable(context, R.drawable.edittext_background_error)
        } else{
            if(msg != null) textInputLayout.helperText = context.getText(msg)
            textInputLayout.error = null
            editText.background =
                    if (editText.isFocused)
                        ContextCompat.getDrawable(context, R.drawable.edittext_background_focus)
                    else
                        ContextCompat.getDrawable(context, R.drawable.edittext_background)
        }
    }

    //set next button on SurveyFragment
    @BindingAdapter("enableNext")
    @JvmStatic
    fun setEnableAndBgColor(view: TextView, activeIndex: LiveData<Int>){

        activeIndex.observe(view.context as LifecycleOwner, {
            view.isEnabled = it >= 0
            view.background =
                if(it >= 0)
                    ContextCompat.getDrawable(view.context, R.drawable.btn_round_green_dark)
                else
                    ContextCompat.getDrawable(view.context, R.drawable.btn_round_green_dark_transparent)
        })
    }

    //set answer buttons on SurveyFragment
    @BindingAdapter("answers", "viewModel")
    @JvmStatic
    fun setAnswers(parent: LinearLayout, surveyData: SurveyData, viewModel: OnboardingViewModel){
        val options = surveyData.options
        val context = parent.context
        val dm = context.resources.displayMetrics

        parent.removeAllViews()
        for (i in options.indices){

            val tvAnswer = TextView(parent.context)
            tvAnswer.text = options[i].content
            tvAnswer.gravity = CENTER

            if(options[i].optionIdx == surveyData.selectedIdx.value){
                tvAnswer.setTextColor(context.getColor(R.color.white))
                tvAnswer.background = ContextCompat.getDrawable(context, R.drawable.btn_round_green)
            } else {
                tvAnswer.setTextColor(context.getColor(R.color.text_gray))
                tvAnswer.background = ContextCompat.getDrawable(context, R.drawable.btn_round_transparent)
            }

            val typeface = Typeface.DEFAULT_BOLD
            tvAnswer.typeface = typeface

            val paddingSize = (25 * dm.density).roundToInt()
            tvAnswer.setPadding(0, paddingSize, 0, paddingSize)

            val params = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val marginSize = (8 * dm.density).roundToInt()
            params.topMargin = marginSize
            params.bottomMargin = marginSize
            tvAnswer.layoutParams = params

            tvAnswer.setOnClickListener {
                viewModel.onOptionClick(options[i].optionIdx)

                for(j in options.indices){
                    val tv = parent.getChildAt(j) as TextView

                    if(options[j].optionIdx == surveyData.selectedIdx.value){
                        tv.setTextColor(context.getColor(R.color.white))
                        tv.background = ContextCompat.getDrawable(context, R.drawable.btn_round_green)
                    } else {
                        tv.setTextColor(context.getColor(R.color.text_gray))
                        tv.background = ContextCompat.getDrawable(context, R.drawable.btn_round_transparent)
                    }
                }
            }

            parent.addView(tvAnswer)
        }
    }

    //setImage
    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, imgUrl: String?){
        Glide.with(view.context).load(imgUrl).into(view)
    }

    @BindingAdapter("roundImgUrl")
    @JvmStatic
    fun setRoundImageUrl(view: ImageView, imgUrl: String?){
        Glide.with(view.context).load(imgUrl)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(view.context, R.drawable.image_camera))
                .into(view)
    }

    //set PlantAdapter
    @BindingAdapter("plantItems", "listeningViewModel")
    @JvmStatic
    fun setPlantAdapter(view: RecyclerView,
                        itemViewModels: LiveData<List<PlantItemViewModel>>,
                        viewModel: EncyclopediaViewModel?){
        if (view.adapter == null){
            val plantAdapter = PlantAdapter(view.context,
                    (view.layoutManager as LinearLayoutManager).orientation == LinearLayout.HORIZONTAL)
            if (viewModel != null)
                plantAdapter.deleteSearchedPlantListener = viewModel
            view.adapter = plantAdapter
        }

        if(itemViewModels.value != null)
                (view.adapter as PlantAdapter).updateItems(itemViewModels.value!!)
    }

    //set PagingPlantAdapter
    @BindingAdapter("pagingPlantItems", "listeningViewModel", "refreshing")
    @JvmStatic
    fun setPagingPlantAdapter(view: RecyclerView,
                              itemViewModels: LiveData<List<PlantItemViewModel>>,
                              viewModel: EncyclopediaViewModel,
                              refreshing: LiveData<Boolean>){

        if (view.adapter == null){
            val pagingPlantAdapter = PagingPlantAdapter(view.context)
            view.adapter = pagingPlantAdapter
        }

        val mAdapter = view.adapter as PagingPlantAdapter

        if (itemViewModels.value != null) {
            if (itemViewModels.value!!.size == mAdapter.itemCount-1 && !refreshing.value!!){

                mAdapter.setLastPage(true)
                view.clearOnScrollListeners()

            } else {

                mAdapter.setLastPage(false)
                mAdapter.updateItems(itemViewModels.value!!)

                view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val lastVisiblePosition =
                                (view.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (lastVisiblePosition == mAdapter.itemCount-1){
                            Thread.sleep(300)
                            viewModel.onLoadMore()
                        }
                    }
                })
            }
        }
    }

    //set MyPlantAdapter
    @BindingAdapter("myPlantItems", "listeningViewModel")
    @JvmStatic
    fun setMyPlantAdapter(view: RecyclerView, itemViewModels: LiveData<List<MyPlantItemViewModel>>, viewModel: MyGardenViewModel){
        if (view.adapter == null){
            val myPlantAdapter = MyPlantAdapter(view.context)
            myPlantAdapter.deleteMyPlantListener = viewModel
            view.adapter = myPlantAdapter
        }

        if(itemViewModels.value != null)
                (view.adapter as MyPlantAdapter).updateItems(itemViewModels.value!!)
    }

    //set DiaryAdapter
    @BindingAdapter("diaryItems", "listeningViewModel")
    @JvmStatic
    fun setDiaryAdapter(view: RecyclerView, itemViewModels: LiveData<List<DiaryItemViewModel>>, viewModel: MyPlantDetailsViewModel){
        if (view.adapter == null){
            val diaryAdapter = DiaryAdapter(view.context,
                    (view.layoutManager as LinearLayoutManager).orientation == LinearLayout.HORIZONTAL)
            diaryAdapter.deleteMyDiaryListener = viewModel
            view.adapter = diaryAdapter
        }

        if(itemViewModels.value != null)
            (view.adapter as DiaryAdapter).updateItems(itemViewModels.value!!)
    }

    //set HomeMyPlantAdapter
    @BindingAdapter("homeMyPlantItems")
    @JvmStatic
    fun setHomeMyPlantAdapter(view: RecyclerView, itemViewModels: LiveData<List<HomeMyPlantItemViewModel>>){
        if (view.adapter == null){
            val homeMyPlantAdapter = HomeMyPlantAdapter(view.context)
            view.adapter = homeMyPlantAdapter
        }

        if (itemViewModels.value != null)
            (view.adapter as HomeMyPlantAdapter).updateItems(itemViewModels.value!!)
    }

    //set ImagePagerAdapter
    @BindingAdapter("imageItems", "currentPage")
    @JvmStatic
    fun setImagePagerAdapter(pager: ViewPager2, items: LiveData<List<String>>, currentPage: MutableLiveData<Int>){
        if (pager.adapter == null){
            val imagePagerAdapter = ImagePagerAdapter(pager.context)
            pager.adapter = imagePagerAdapter
        }

        if (items.value != null)
            (pager.adapter as ImagePagerAdapter).updateItems(items.value!!)

        pager.setPageTransformer(MarginPageTransformer(convertDpToPx(pager.context, 10)))

        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage.value = position+1
            }
        })
    }

    //set enabled to all child views (except ViewPager)
    @BindingAdapter("enabledToAllChild")
    @JvmStatic
    fun setEnabledToAllChildViews(view: View, enabled: Boolean){
        view.isEnabled = enabled

        if (view is ViewGroup){
            for(i in 0 until view.childCount){
                val child = view.getChildAt(i)
                if (child !is ViewPager)
                    setEnabledToAllChildViews(child, enabled)
            }
        }
    }

    //searching on Encyclopedia
    @BindingAdapter("searchingViewModel")
    @JvmStatic
    fun setSearching(view: EditText, viewModel: BaseViewModel){
        view.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if(viewModel is EncyclopediaViewModel)
                    viewModel.searchPlantsClick()
                else
                    viewModel.clearInput()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    //set animation for delete btn in MyPlantItem
    @BindingAdapter("showing")
    @JvmStatic
    fun setShowingAnimation(view: View, showing: Boolean){
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.left_fade_in)
        view.visibility = if (showing) View.VISIBLE else View.GONE

        if(showing) view.startAnimation(animation)
    }

    //set animation for delete btn in DiaryItem
    @BindingAdapter("showingHorizontal")
    @JvmStatic
    fun setShowingHorizontalAnimation(view: View, showing: Boolean){
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.down_fade_in)
        view.visibility = if (showing) View.VISIBLE else View.GONE

        if(showing) view.startAnimation(animation)
    }

    @BindingAdapter("shaking")
    @JvmStatic
    fun setShakingAnimation(view: View, shaking: Boolean){
        val shakeAnim = AnimationUtils.loadAnimation(view.context, R.anim.shake)
        if (shaking){
            view.startAnimation(shakeAnim)
        }
    }

    @BindingAdapter("weatherClick")
    @JvmStatic
    fun setWeatherClick(view: ImageView, strWeather: String){
        val animBig = AnimationUtils.loadAnimation(view.context, R.anim.scale_big)
        val animSmall = AnimationUtils.loadAnimation(view.context, R.anim.scale_small)

        if (view.contentDescription.toString() == strWeather){
            view.startAnimation(animBig)
        } else if(view.animation != null && view.animation.fillAfter) {
            view.startAnimation(animSmall)
        }
    }

    @BindingAdapter("searchingFilter")
    @JvmStatic
    fun setFilterSpinner(spinner: Spinner, viewModel: EncyclopediaViewModel){

        val mContext = spinner.context

        val filterList = arrayOf(mContext.getString(R.string.filter_popularity), mContext.getString(R.string.filter_name))

        spinner.adapter =
                ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item, filterList)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.changeFilter(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner.setSelection(viewModel.filterIdx)
    }
}