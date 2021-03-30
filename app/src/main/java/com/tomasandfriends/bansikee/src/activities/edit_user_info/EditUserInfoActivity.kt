package com.tomasandfriends.bansikee.src.activities.edit_user_info

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.gun0912.tedpermission.PermissionListener
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityEditUserInfoBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity
import com.tomasandfriends.bansikee.src.utils.FileUtils
import com.tomasandfriends.bansikee.src.utils.SystemUtils
import java.io.File
import java.util.ArrayList

class EditUserInfoActivity : BaseActivity<ActivityEditUserInfoBinding, EditUserInfoViewModel>() {

    private var tmpUri: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_edit_user_info
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(EditUserInfoViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.editImgEvent.observe(this, {
            //get photo from gallery or camera
            FileUtils.tedPermissionForPhoto(this, object : PermissionListener {
                override fun onPermissionGranted() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        showChoosingPhotoDialog()
                    }
                }

                override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {}
            })
        })

        viewModel.editFinishEvent.observe(this, {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.ask_finish_editing))
                .setPositiveButton(R.string.yes) {_, _ ->
                    viewModel.editUserInfo()
                }.setNegativeButton(R.string.no, null)
                .create().show()
        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showChoosingPhotoDialog(){

        AlertDialog.Builder(this)
            .setTitle(R.string.get_photo_from)
            .setItems(arrayOf(getString(R.string.choose_from_album),
                getString(R.string.take_photo_from_cam),
                getString(R.string.set_default_image),
                getString(R.string.cancel))) { _, which ->
                when(which){
                    0 -> {
                        val albumIntent = Intent(Intent.ACTION_PICK)
                        albumIntent.type = MediaStore.Images.Media.CONTENT_TYPE
                        resultLauncher.launch(albumIntent)
                    }

                    1 -> {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        tmpUri = FileUtils.createCacheFile(this)
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tmpUri)
                        resultLauncher.launch(cameraIntent)
                    }

                    2 -> {
                        viewModel.setPhotoStrUrl("")
                    }
                }
            }.create().show()
    }

    //onActivityResult
    @RequiresApi(Build.VERSION_CODES.P)
    private var resultLauncher =
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
                    viewModel.setPhotoStrUrl(imgPath)

                    if(photoUri.toString().contains(FileUtils.FILE_PROVIDER_AUTHORITY)){
                        val cacheFile = File(externalCacheDir!!.absolutePath+"/"+photoUri.lastPathSegment)
                        if (cacheFile.exists()) cacheFile.delete()
                    }
                }
            }
        }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.ask_exit_while_editing))
            .setPositiveButton(R.string.yes) {_, _ ->
                finish()
            }.setNegativeButton(R.string.no, null)
            .create().show()
    }
}