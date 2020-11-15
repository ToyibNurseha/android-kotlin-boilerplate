package com.toyibnurseha.kotlin_boiler_plate

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object Helper {

    @SuppressLint("InflateParams")
    fun progressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    fun closeKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getTimeToSeconds(): Long {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
    }

    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun resizeBitmap(bitmap: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newWidth.toFloat() / height
        //create matrix for manipulation
        val matrix = Matrix()
        //resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)
        //recreate new bitmap
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
    }

    fun parseDate(dateTime: String, format: String): String {
        var formattedDate: String? = null
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")

        try {
            val date = sdf.parse(dateTime)
            date?.let {
                val outputFormat = SimpleDateFormat(format, Locale.getDefault())
                outputFormat.timeZone = TimeZone.getDefault()
                formattedDate = outputFormat.format(date)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return formattedDate.toString()
    }

    fun checkCameraPermission(context: Context): Boolean {
        val externalStoragePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        val cameraPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == (PackageManager.PERMISSION_GRANTED)
        return externalStoragePermission && cameraPermission
    }

    fun checkGalleryPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
    }

    fun cameraRequestPermissionActivity(activity: Activity, requestCode: Int) {
        EasyPermissions.requestPermissions(
            activity,
            "To access your camera we need your permission",
            requestCode,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    fun galleryRequestPermissionActivity(activity: Activity, requestCode: Int) {
        EasyPermissions.requestPermissions(activity, "To access your gallery we need your permission", requestCode, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}