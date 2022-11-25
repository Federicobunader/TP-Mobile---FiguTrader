package com.example.figutrader.ui.edicion_figurita

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.authentication.AuthenticationAPIClient
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentEdicionFiguBinding
import com.example.figutrader.ui.album.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EdicionFiguritaFragment : Fragment() {

    private var _binding: FragmentEdicionFiguBinding? = null

    private lateinit var recyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var figuritaActual :FiguritaDataView? = null

    var cantidadText: TextView = binding.CantidadTextView
    var nombreText: TextView = binding.figuritaNombre

    var edicionFiguritaViewModel : EdicionFiguritaViewModel? = null

    public fun setFiguritaActual(figuritaDataView: FiguritaDataView){
        figuritaActual = figuritaDataView
        Log.v("EdicionFiguritaFragment", figuritaDataView.descripcion)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        edicionFiguritaViewModel = ViewModelProvider(requireActivity()).get(EdicionFiguritaViewModel::class.java)

        _binding = FragmentEdicionFiguBinding.inflate(inflater, container, false)

        cantidadText = binding.CantidadTextView
        nombreText = binding.figuritaNombre

        val root: View = binding.root

        edicionFiguritaViewModel!!.figuritasData.observe(viewLifecycleOwner) {
            cantidadText.text = it.cantidad.toString()
            nombreText.text = it.descripcion
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("EdicionFiguritaFragment", "${figuritaActual?.descripcion}")


        binding.GuardarCantidadButton.setOnClickListener {
            Log.e("ENTRE AL CLICK"," ENTREEEE");
            edicionFiguritaViewModel?.userIdData?.observe(viewLifecycleOwner) {
                val figurita =
                    FiguritaUsuarioData(figuritaActual!!.cantidad, figuritaActual!!.figuId)

                Log.e("TAG",figurita.figuId.toString());

                val figuPost = AlbumClient.service.addFigurita(it.toString(), figurita)
                figuPost.enqueue(object : Callback<List<FiguritaUsuarioResult>> {
                    override fun onResponse(
                        call: Call<List<FiguritaUsuarioResult>>?,
                        response: Response<List<FiguritaUsuarioResult>>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            Log.v("retrofit", "call successful ${body?.size}")
                        } else
                            Log.v("retrofit", "call is not successful")
                    }

                    override fun onFailure(call: Call<List<FiguritaUsuarioResult>>, t: Throwable) {
                        Log.v("retrofit", "call failed")
                    }
                })
            }
/*
        Log.v("AlbumFragment", "${AlbumDataset.album?.size}")
        val albumDataset : List<FiguritaDataView> = AlbumDataset.album
            ?.map {
                    figu -> FiguritaDataView(
                        figu.descripcion,
                AlbumDataset.albumUsuario?.find { it.figuId == figu.figuId }?.cantidad ?: 0,
                        figu.categoria,
                        figu.figuId
                    )
            }
            ?: emptyList()

        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = FiguritasAdapter(albumDataset)

        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}