package com.example.figutrader.ui.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentAlbumBinding
import com.example.figutrader.ui.edicion_figurita.EdicionFiguritaFragment
import com.example.figutrader.ui.edicion_figurita.EdicionFiguritaViewModel

class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private var _edicionFiguBinding: EdicionFiguritaFragment? = null

    private lateinit var recyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val albumViewModel =
            ViewModelProvider(this).get(AlbumViewModel::class.java)

        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.v("AlbumFragment", "${AlbumDataset.album?.size}")
        val albumDataset : List<FiguritaDataView> = AlbumDataset.album
            ?.map {
                    figu -> FiguritaDataView(
                        figu.descripcion,
                AlbumDataset.albumUsuario?.find { it.figuId == figu.figuId }?.cantidad ?: 0,
                        figu.categoria,
                        figu.figuId,
                AlbumDataset.albumUsuario?.find { it.figuId == figu.figuId }?.usuarioId ?:""
                    )
            }
            ?: emptyList()
        val edicionFiguritaViewModel = ViewModelProvider(requireActivity()).get(EdicionFiguritaViewModel::class.java)

        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = FiguritasAdapter(albumDataset, object : FiguClickedListener{
            override fun onFiguClicked(figurita: FiguritaDataView) {
                edicionFiguritaViewModel.setFigurita(figurita)
                findNavController().navigate(R.id.nav_edicion_figurita)
            }
        })

        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
/*
        binding.camButton.setOnClickListener {
            findNavController().navigate(R.id.nav_scan)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}