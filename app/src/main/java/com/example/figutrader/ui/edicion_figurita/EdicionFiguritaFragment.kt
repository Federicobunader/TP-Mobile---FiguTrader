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

    public fun setFiguritaActual(figuritaDataView: FiguritaDataView){
        figuritaActual = figuritaDataView
        Log.v("EdicionFiguritaFragment", figuritaDataView.descripcion)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEdicionFiguBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val edicionFiguritaViewModel = ViewModelProvider(requireActivity()).get(EdicionFiguritaViewModel::class.java)

        Log.v("EdicionFiguritaFragment", "${figuritaActual?.descripcion}")
        val cantidadText: TextView = binding.CantidadTextView
        val nombreText: TextView = binding.figuritaNombre
        val nuevaCantidadText: TextView = binding.NuevaCantidadText

        edicionFiguritaViewModel.figuritasData.observe(viewLifecycleOwner) {
            cantidadText.text = it.cantidad.toString()
            nombreText.text = it.descripcion
            figuritaActual = it
        }

        binding.GuardarCantidadButton.setOnClickListener {
            val nuevaCantidad : String = view.findViewById<EditText>(R.id.NuevaCantidadText).text.toString()
            Log.v("EdicionFiguritaFragment", "Click Boton cantidad")

            val figuData = FiguritaUsuarioData(nuevaCantidad.toInt(), figuritaActual?.figuId ?: 0)
            val albumUsuarioCall = AlbumClient.service.addFigurita(figuritaActual?.usuarioId ?: "migue", figuData)

            Log.v("EdicionFiguritaFragment", "UsuarioId: ${figuritaActual?.usuarioId ?: "migue"} FiguId: ${figuData.figuId}, cantidad: ${figuData.cantidad}")

            albumUsuarioCall.enqueue(object : Callback<List<FiguritaUsuarioResult>> {
                override fun onResponse(call: Call<List<FiguritaUsuarioResult>>?, response: Response<List<FiguritaUsuarioResult>>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        Log.v("retrofit", "call POST successful ${body?.size}")
                        nuevaCantidadText.text = ""
                        cantidadText.text = figuData.cantidad.toString()
                    }
                    else
                        Log.v("retrofit", "call POST is not successful")
                }

                override fun onFailure(call: Call<List<FiguritaUsuarioResult>>, t: Throwable) {
                    Log.v("retrofit", "call POST failed")
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}