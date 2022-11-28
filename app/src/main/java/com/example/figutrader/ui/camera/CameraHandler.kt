package com.example.figutrader.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.reflect.KFunction0

class CameraHandler(
    classifyImage: (Bitmap) -> Unit,
    getImage: (ActivityResult) -> Bitmap,
    registerForActivityResult: (contract: ActivityResultContracts.StartActivityForResult, callback: ActivityResultCallback<ActivityResult>) -> ActivityResultLauncher<Intent>
) {
    private lateinit var lastImageBitmap: Bitmap

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
//            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            val imageBitmap = getImage(result)
            lastImageBitmap= imageBitmap
            classifyImage(imageBitmap)
        }
    }

    fun launchTakePictureIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }

    fun launchPickPictureIntent() {
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(cameraIntent)
    }
}