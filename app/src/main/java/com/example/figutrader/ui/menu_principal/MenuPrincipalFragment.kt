package com.example.figutrader.ui.menu_principal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentMenuPrincipalBinding
import com.example.figutrader.ui.album.AlbumClient
import com.example.figutrader.ui.album.FiguritaResult
import com.example.figutrader.ui.album.FiguritaUsuarioResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuPrincipalFragment : Fragment() {

    private var _binding: FragmentMenuPrincipalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var userId: String? = "userId"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuPrincipalViewModel =
            ViewModelProvider(requireActivity()).get(MenuPrincipalViewModel::class.java)

        _binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val albumName: TextView = binding.editTextTextPersonName2
        val progressBar = binding.progressBar
        var progressTextView = binding.progressTextView
        var totalTextView = binding.totalTextView

        val albumCall = AlbumClient.service.getAlbum()
        albumCall.enqueue(object : Callback<List<FiguritaResult>> {
            override fun onResponse(call: Call<List<FiguritaResult>>?, response: Response<List<FiguritaResult>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.v("retrofit", "call successful ${body?.size}")
                    progressBar.max = body?.size ?: 200
                    totalTextView.text = "${body?.size}"
                }
                else
                    Log.v("retrofit", "call is not successful")
            }

            override fun onFailure(call: Call<List<FiguritaResult>>, t: Throwable) {
                Log.v("retrofit", "call failed")
            }
        })

        menuPrincipalViewModel.userIdData.observe(viewLifecycleOwner) {
            Log.v("MenuPrincipalUserId", "$it")

            val albumUsuarioCall = AlbumClient.service.getAlbumUsuario(it.toString())
            albumUsuarioCall.enqueue(object : Callback<List<FiguritaUsuarioResult>> {
                override fun onResponse(call: Call<List<FiguritaUsuarioResult>>?, response: Response<List<FiguritaUsuarioResult>>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        Log.v("retrofit", "call successful ${body?.size}")
                        progressBar.progress = body?.size ?: 0
                        progressTextView.text = "${body?.size}"
                    }
                    else
                        Log.v("retrofit", "call is not successful")
                }

                override fun onFailure(call: Call<List<FiguritaUsuarioResult>>, t: Throwable) {
                    Log.v("retrofit", "call failed")
                }
            })
        }

        menuPrincipalViewModel.userNameData.observe(viewLifecycleOwner) {
            Log.v("MenuPrincipalUserName", "$it")
            albumName.text = it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.camButton.setOnClickListener {
            findNavController().navigate(R.id.nav_scan)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}