package com.example.figutrader.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.figutrader.databinding.FragmentScanBinding
import com.example.figutrader.ui.camera.CameraHandler

class ScanFragment : Fragment() {
    private lateinit var binding: FragmentScanBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 100);

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameraHandler = CameraHandler(::afterTakingPhoto, ::registerForActivityResult)

        binding.buttonScan.setOnClickListener { cameraHandler.launchTakePictureIntent() }
    }

    private fun afterTakingPhoto(photoBitmap: Bitmap) {

    }


}