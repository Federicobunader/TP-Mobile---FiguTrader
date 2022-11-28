package com.example.figutrader.ui.scan

//import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888

import android.Manifest
import android.R.attr.data
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentScanBinding
import com.example.figutrader.ml.FiguTraderModel
import com.example.figutrader.ui.camera.CameraHandler
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import com.example.figutrader.model.AlbumDataset
import com.example.figutrader.model.AlbumClient
import com.example.figutrader.model.FiguritaUsuarioData
import com.example.figutrader.model.FiguritaUsuarioResult


class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null

    private val binding get() = _binding!!


    var imageSize = 32
    var reqCode : Int = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)

        var camera : Button = binding.buttonScan
        var gallery : Button = binding.galleryButton


        var cameraHandler = CameraHandler(::classifyImage, ::getImage, ::registerForActivityResult)

        camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                reqCode = 3
                cameraHandler.launchTakePictureIntent()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 100);
            }
        }

        gallery.setOnClickListener {
            reqCode = 1
            cameraHandler.launchPickPictureIntent()
        }

        return binding.root
    }

    fun classifyImage(image: Bitmap) {
        try {
//            val model: Model = Model.newInstance(context)
            val model = FiguTraderModel.newInstance(requireContext())

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            inputFeature0.loadBuffer(byteBuffer)
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(linkedSetOf("10", "Angel Di Maria"), linkedSetOf("18", "Lautaro Martinez"), linkedSetOf("19", "Lionel Messi"))

            cargarFigurita(classes[maxPos].elementAt(0), classes[maxPos].elementAt(1))

            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    fun getImage(result : ActivityResult): Bitmap {
        if (reqCode === 3) {
            var image = result.data?.extras?.get("data") as Bitmap
            val dimension = image.width.coerceAtMost(image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            return image
        } else {
            val dat: Uri? = result.data?.data
            var image: Bitmap? = null
            try {
                image = MediaStore.Images.Media.getBitmap(context?.contentResolver, dat)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
            return image
        }
    }

    fun cargarFigurita(figuritaId : String, figuritaName : String) {
        val figuData = FiguritaUsuarioData(1, figuritaId.toInt())
        val albumUsuarioCall = AlbumClient.service.addFigurita(AlbumDataset.usuarioId!!, figuData)
        albumUsuarioCall.enqueue(object : Callback<List<FiguritaUsuarioResult>> {
            override fun onResponse(
                call: Call<List<FiguritaUsuarioResult>>?,
                response: Response<List<FiguritaUsuarioResult>>
            ) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.nav_menu_principal)
                    Toast.makeText(context, "Se agregó con éxito a $figuritaName", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<FiguritaUsuarioResult>>, t: Throwable) {
                Log.v("retrofit", "call POST failed")
            }
        })

    }

}

