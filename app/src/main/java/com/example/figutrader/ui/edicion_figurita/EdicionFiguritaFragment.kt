package com.example.figutrader.ui.edicion_figurita

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentEdicionFiguBinding
import com.example.figutrader.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EdicionFiguritaFragment : Fragment() {

    private var _binding: FragmentEdicionFiguBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var figuritaActual: FiguritaDataView? = null

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

        val edicionFiguritaViewModel =
            ViewModelProvider(requireActivity()).get(EdicionFiguritaViewModel::class.java)

        val cantidadText: TextView = binding.CantidadTextView
        val descripcionText: TextView = binding.DescripcionTextView
        val nuevaCantidadText: TextView = binding.NuevaCantidadText
        val categoriaText: TextView = binding.CategoriaTextView
        val figuText: TextView = binding.FiguTextView

        edicionFiguritaViewModel.figuritasData.observe(viewLifecycleOwner) {
            cantidadText.text = it.cantidad.toString()
            descripcionText.text = it.descripcion
            categoriaText.text = it.categoria
            actualizarImagen(it.figuId, it.cantidad)
            figuritaActual = it
        }

        binding.GuardarCantidadButton.setOnClickListener {
            val nuevaCantidad: String =
                view.findViewById<EditText>(R.id.NuevaCantidadText).text.toString()

            if (nuevaCantidad != "") {
                val figuData =
                    FiguritaUsuarioData(nuevaCantidad.toInt(), figuritaActual?.figuId ?: 0)

                val tokenUsuario: String? = if (figuritaActual?.usuarioId != "") {
                    figuritaActual?.usuarioId
                } else {
                    AlbumDataset.usuarioId
                }

                val albumUsuarioCall = AlbumClient.service.addFigurita(tokenUsuario!!, figuData)
                albumUsuarioCall.enqueue(object : Callback<List<FiguritaUsuarioResult>> {
                    override fun onResponse(
                        call: Call<List<FiguritaUsuarioResult>>?,
                        response: Response<List<FiguritaUsuarioResult>>
                    ) {
                        if (response.isSuccessful) {
                            nuevaCantidadText.text = ""
                            cantidadText.text = figuData.cantidad.toString()
                            val body = response.body()
                            AlbumDataset.albumUsuario = body
                            actualizarImagen(figuData.figuId, figuData.cantidad)
                            Toast.makeText(context, "Se guardó la cantidad con éxito", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<FiguritaUsuarioResult>>, t: Throwable) {
                        Log.v("retrofit", "call POST failed")
                    }
                })
            }
        }
    }

    fun actualizarImagen(figuId : Int, cantidad : Int){
        if (cantidad > 0) {
            val imagenName = "f$figuId"
            binding.FiguTextView.setBackgroundResource(
                requireContext().resources.getIdentifier(
                    imagenName,
                    "drawable",
                    requireContext().packageName
                )
            )
        }
        else {
            binding.FiguTextView.setBackgroundResource(R.drawable.figurita)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}