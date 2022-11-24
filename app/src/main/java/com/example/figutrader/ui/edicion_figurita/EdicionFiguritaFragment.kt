package com.example.figutrader.ui.edicion_figurita

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.figutrader.databinding.FragmentEdicionFiguBinding
import com.example.figutrader.ui.album.FiguritaDataView

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
        val edicionFiguritaViewModel = ViewModelProvider(this).get(EdicionFiguritaViewModel::class.java)

        _binding = FragmentEdicionFiguBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("EdicionFiguritaFragment", "${figuritaActual?.descripcion}")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}