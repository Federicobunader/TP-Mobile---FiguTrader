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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuPrincipalFragment : Fragment() {

    private var _binding: FragmentMenuPrincipalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var userId: String? = "hola"

    fun setUserId(id: String?) {
        this.userId = id
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuPrincipalViewModel =
            ViewModelProvider(requireActivity()).get(MenuPrincipalViewModel::class.java)

        _binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.editTextTextPersonName2
        val progressBar = binding.progressBar
        var progressTextView = binding.progressTextView

        val albumCall = AlbumClient.service.getAlbum()
        albumCall.enqueue(object : Callback<List<FiguritaResult>> {
            override fun onResponse(call: Call<List<FiguritaResult>>?, response: Response<List<FiguritaResult>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.v("retrofit", "call successful ${body?.size}")
                    progressBar.max = body?.size ?: 0
                    progressBar.progress = 10
                    progressTextView.text = "10/${body?.size}"
                }
                else
                    Log.v("retrofit", "call is not successful")
            }

            override fun onFailure(call: Call<List<FiguritaResult>>, t: Throwable) {
                Log.v("retrofit", "call failed")
            }
        })



        /*
        menuPrincipalViewModel.userIdData.observe(viewLifecycleOwner) {
            val service = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://635ef291ed25a0b5fe4fbaeb.mockapi.io/")
                .build()
                .create(FiguritaService::class.java)

            val call = it?.let { it1 -> service.getFiguritas(it1.drop(6).first().toString()) }

            call?.enqueue(object : Callback<List<FiguritaResponse>> {
                override fun onResponse(call: Call<List<FiguritaResponse>>?, response: Response<List<FiguritaResponse>>?) {
                    Log.e("test", response?.code().toString())
                    if (response?.code() == 200) {
                        val figuritaResponse = response.body()!!

                        val stringBuilder = "Country: " + figuritaResponse[0].categoria!!

                        Log.e("test", stringBuilder)
                    }
                }

                override fun onFailure(call: Call<List<FiguritaResponse>>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                }
            })
        }

        menuPrincipalViewModel.userNameData.observe(viewLifecycleOwner) {
            Log.e("test", "entre al observer2")
            textView.text = it
        }


        userId?.let { Log.e("test", it) }

*/
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