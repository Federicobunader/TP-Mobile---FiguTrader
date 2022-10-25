package com.example.figutrader.ui.menu_principal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.auth0.android.result.UserProfile
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentMenuPrincipalBinding

class MenuPrincipalFragment : Fragment() {

    private var _binding: FragmentMenuPrincipalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var username: String = "";

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.i("username  AHHHH", username)
        var title = "Album de " + username;
        Log.i("title",
            title
        )

        binding.editTextTextPersonName2.text = title//(activity as MainActivity).cachedUserProfile!!.name
        Log.i("binding.editTextTextPersonName2.text",
            binding.editTextTextPersonName2.text as String
        )

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.camButton.setOnClickListener {
            findNavController().navigate(R.id.nav_scan)
        }
        Log.i("username  AHHHH", username)
        var title = "Album de " + username;
        Log.i("title",
            title
        )

        binding.editTextTextPersonName2.text = title//(activity as MainActivity).cachedUserProfile!!.name
        Log.i("binding.editTextTextPersonName2.text",
            binding.editTextTextPersonName2.text as String
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setUsername(user: String?) {
        Log.i("user ???", user!!)
        username = user!!
        Log.i("username ???", username!!)
    }
}