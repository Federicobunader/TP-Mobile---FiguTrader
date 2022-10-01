package com.example.figutrader.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class CameraHandler (
    afterTakingPhoto: (Bitmap) -> Unit,
    registerForActivityResult: (contract: ActivityResultContracts.StartActivityForResult, callback: ActivityResultCallback<ActivityResult>) -> ActivityResultLauncher<Intent>
) {
    private lateinit var lastImageBitmap: Bitmap
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            lastImageBitmap= imageBitmap
            afterTakingPhoto(imageBitmap)
        }
    }

    fun launchTakePictureIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }
}