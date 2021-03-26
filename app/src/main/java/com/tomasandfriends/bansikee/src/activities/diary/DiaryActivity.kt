package com.tomasandfriends.bansikee.src.activities.diary

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.NumberPicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.gun0912.tedpermission.PermissionListener
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityDiaryBinding
import com.tomasandfriends.bansikee.databinding.DialogPlantHeightBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity
import com.tomasandfriends.bansikee.src.utils.FileUtils
import com.tomasandfriends.bansikee.src.utils.SystemUtils
import com.tomasandfriends.bansikee.src.utils.SystemUtils.convertDpToPx
import java.io.File
import java.lang.Math.min
import java.util.ArrayList

class DiaryActivity: BaseActivity<ActivityDiaryBinding, DiaryViewModel>() {

    private var tmpUri: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_diary
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(DiaryViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getPhotosEvent.observe(this, {
            FileUtils.tedPermissionForPhoto(this, object : PermissionListener {
                override fun onPermissionGranted() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        showChoosingPhotoDialog()
                    }
                }

                override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {}
            })
        })

        viewModel.editHeightEvent.observe(this, {
            showEditHeightDialog(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.getBundleExtra("bundle") == null){
            viewModel.getDiaryData(intent.getIntExtra("diaryIdx", 0))
        }else{
            viewModel.initWriteDiary(intent.getBundleExtra("bundle")!!)
        }
    }

    private fun showEditHeightDialog(lastHeight: Int){

        val dialogHeightBinding = DialogPlantHeightBinding.inflate(layoutInflater)
        dialogHeightBinding.root.minimumHeight = convertDpToPx(this, 100)
        dialogHeightBinding.npHeight.minValue = 0
        dialogHeightBinding.npHeight.maxValue = 300
        dialogHeightBinding.npHeight.value = lastHeight

        AlertDialog.Builder(this)
                .setMessage(R.string.edit_plant_height)
                .setView(dialogHeightBinding.root)
                .setPositiveButton(R.string.check) { _, _ ->
                    viewModel.setHeight(dialogHeightBinding.npHeight.value)
                }.setNegativeButton(R.string.cancel, null)
                .create().show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showChoosingPhotoDialog(){

        AlertDialog.Builder(this)
                .setTitle(R.string.get_photo_from)
                .setItems(arrayOf(getString(R.string.choose_from_album),
                        getString(R.string.take_photo_from_cam),
                        getString(R.string.cancel))) { _, which ->
                    when(which){
                        0 -> {
                            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
                            albumIntent.type = "image/*"
                            albumIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                            albumResultLauncher.launch(Intent.createChooser(albumIntent, getString(R.string.choose_picker_app)))
                        }

                        1 -> {
                            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            tmpUri = FileUtils.createCacheFile(this)
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tmpUri)
                            cameraResultLauncher.launch(cameraIntent)
                        }
                    }
                }.create().show()
    }

    //onActivityResult
    @RequiresApi(Build.VERSION_CODES.P)
    private val albumResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
                if (result.resultCode == RESULT_OK && result.data != null){
                    val imgUrls = ArrayList<String>()

                    val clipData = result.data!!.clipData

                    if (clipData != null){

                        for (i in 0 until 5.coerceAtMost(clipData.itemCount)){

                            val imgUrl =
                                    FileUtils.getPathOfImageFileResizing(this, clipData.getItemAt(i).uri)
                            imgUrls.add(imgUrl)
                        }

                    } else if(result.data!!.data != null){
                        imgUrls.add(FileUtils.getPathOfImageFileResizing(this, result.data!!.data!!))
                    }

                    viewModel.setPhotos(imgUrls)
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    private var cameraResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK){

                    val photoUri =
                            if(result.data == null || result.data!!.data == null)
                                tmpUri
                            else
                                result.data!!.data
                    val imgPath = FileUtils.getPathOfImageFileResizing(this, photoUri!!)

                    if (imgPath.isEmpty())
                        SystemUtils.showAlert(this, R.string.app_name, R.string.upload_image_error)
                    else{
                        viewModel.setPhotos(arrayListOf(imgPath))

                        val cacheFile = File(externalCacheDir!!.absolutePath+"/"+photoUri.lastPathSegment)
                        if (cacheFile.exists())
                            cacheFile.delete()
                    }
                }
            }
}