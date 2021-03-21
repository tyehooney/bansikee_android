package com.tomasandfriends.bansikee.src.utils

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tomasandfriends.bansikee.R
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

object FileUtils {

    const val MAX_IMAGE_SIZE = 500 * 1024
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

    @RequiresApi(Build.VERSION_CODES.R)
    fun getPathOfImageFileResizing(context: Context, originalUri: Uri): String? {
        var bitmap: Bitmap?
        val degreeToRotate = getOrientationOfImage(getRealPath(context, originalUri)!!)

        val source = ImageDecoder.createSource(context.contentResolver, originalUri)
        bitmap = ImageDecoder.decodeBitmap(source)
        bitmap = getRotatedBitmap(bitmap, degreeToRotate)

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
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, compressQuality, fout)
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

    @SuppressLint("Recycle")
    fun getRealPath(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null
        var result: String?

        if (uri.toString().contains(FILE_PROVIDER_AUTHORITY))
            result = context.externalCacheDir!!.absolutePath+"/"+uri.lastPathSegment
        else {
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(uri, proj, null, null, null)
                val columnIdx = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(columnIdx)
            } finally {
                cursor?.close()
            }
        }

        return result
    }

    private fun getOrientationOfImage(filePath: String): Int {
        val exif = ExifInterface(filePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

        if (orientation != -1){
            when(orientation){
                ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                ExifInterface.ORIENTATION_ROTATE_270 -> return 270
            }
        }

        return 0
    }

    private fun getRotatedBitmap(bitmap: Bitmap?, degrees: Int): Bitmap? {
        if (bitmap == null) return null
        if (degrees == 0) return bitmap

        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat(), bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2)

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}