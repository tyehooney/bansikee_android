package com.tomasandfriends.bansikee.src.utils

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tomasandfriends.bansikee.R
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

object FileUtils {

    private const val MAX_IMAGE_SIZE = 500 * 1024
    const val FILE_PROVIDER_AUTHORITY = "com.tomasandfriends.bansikee.fileprovider"

    fun createCacheFile(context: Context): Uri{
        val tmpUrl = "tmp_"+System.currentTimeMillis()+".jpg"
        val tmpFile = File(context.externalCacheDir, tmpUrl)
        return FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, tmpFile)
    }

    //check permissions for photo
    fun tedPermissionForPhoto(context: Context, permissionListener: PermissionListener){
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(R.string.permission_2)
            .setDeniedMessage(R.string.permission_1)
            .setPermissions(WRITE_EXTERNAL_STORAGE, CAMERA)
            .check()
    }

@RequiresApi(Build.VERSION_CODES.P)
fun getPathOfImageFileResizing(context: Context, originalUri: Uri): String {
        var bitmap: Bitmap?

        val source = ImageDecoder.createSource(context.contentResolver, originalUri)
        bitmap = ImageDecoder.decodeBitmap(source)

        var mStrUri = ""

        if (bitmap != null){

            var fout: FileOutputStream? = null

            try {
                val dir = context.getExternalFilesDir(DIRECTORY_PICTURES)

                if (!dir!!.exists()) dir.mkdirs()

                var compressQuality = 100
                var streamLength = MAX_IMAGE_SIZE
                val fileName = "bansikee_"+System.currentTimeMillis()+"_pic.jpg"
                val file = File(dir.absolutePath, fileName)

                while (streamLength >= MAX_IMAGE_SIZE){
                    fout = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, fout)
                    streamLength = file.length().toInt()
                    compressQuality -= 5
                }

                fout!!.flush()
                mStrUri = file.path

            } catch (e: Exception){ e.printStackTrace() }
            finally { fout?.close() }
        }

        return mStrUri
    }
}