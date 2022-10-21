package com.example.figutrader.ui.registro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentRegistrarseBinding

class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistrarseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val account = Auth0("OisvgmujDA8VxbbBdvbI1h7itTe2OgjD", "dev-1v0y0xnrlbzoiled.us.auth0.com")
    val authenticationApi = AuthenticationAPIClient(account)
    val signUpCallback = object : Callback<Credentials, AuthenticationException> {
        override fun onFailure(exception: AuthenticationException) {
            findNavController().navigate(R.id.nav_registro)
        }

        override fun onSuccess(credentials: Credentials) {
            findNavController().navigate(R.id.nav_login)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registroViewModel =
            ViewModelProvider(this).get(RegistroViewModel::class.java)

        _binding = FragmentRegistrarseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView2
        registroViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup2.setOnClickListener {
            val password = view.findViewById<EditText>(R.id.inputPasswordSignup).text.toString()
            val passwordConfirm = view.findViewById<EditText>(R.id.inputConfirmPasswordSignup).text.toString()
            val username = view.findViewById<EditText>(R.id.inputUsernameSignup).text.toString()
            val email = view.findViewById<EditText>(R.id.inputEmailSignup).text.toString()
            if(password == passwordConfirm) {
                authenticationApi
                    .signUp(email,password,username,"Username-Password-Authentication")
                    .validateClaims()
                    .start(signUpCallback)
            }
        }
    }
}