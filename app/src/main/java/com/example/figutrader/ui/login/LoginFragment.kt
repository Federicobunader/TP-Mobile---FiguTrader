package com.example.figutrader.ui.login

import android.os.Bundle
import android.util.Log
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
import com.auth0.android.result.UserProfile
import com.example.figutrader.MainActivity
import com.example.figutrader.R
import com.example.figutrader.databinding.FragmentLoginBinding
import com.example.figutrader.ui.menu_principal.MenuPrincipalFragment

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var cachedCredentials: Credentials? = null
    var cachedUserProfile: UserProfile? = null
    var account: Auth0? = null
    val menuPrincipalFragment = MenuPrincipalFragment()

    val userProfileCallBack = object : Callback<UserProfile, AuthenticationException> {
        override fun onFailure(exception: AuthenticationException) {
            Log.e("userProfileCallBack",exception.getDescription())
        }

        override fun onSuccess(userProfile: UserProfile) {
            cachedUserProfile = userProfile
            menuPrincipalFragment.setUsername(userProfile.name);
            Log.i("userProfileCallBack LOGIN", userProfile.getId() + " - mail" + userProfile.email + " - nombre " + userProfile.name)
        }
    }

    val loginCallback = object : Callback<Credentials, AuthenticationException> {
        override fun onFailure(exception: AuthenticationException) {
            Log.e("leonE", exception.getDescription())
            findNavController().navigate(R.id.nav_login)
        }

        override fun onSuccess(credentials: Credentials) {
            cachedCredentials = credentials
            findNavController().navigate(R.id.nav_menu_principal)

            val client = AuthenticationAPIClient(account!!)
            client
                .userInfo(cachedCredentials!!.accessToken!!)
                .start(userProfileCallBack)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registroViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView2
        registroViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        account = (activity as MainActivity).account
        cachedCredentials = (activity as MainActivity).cachedCredentials
        cachedUserProfile = (activity as MainActivity).cachedUserProfile

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.nav_registro)
        }

        binding.buttonLogin.setOnClickListener {
            val password = view.findViewById<EditText>(R.id.inputPasswordLogin).text.toString()
            val username = view.findViewById<EditText>(R.id.inputUserLogin).text.toString()
            val client = AuthenticationAPIClient(account!!)
                .login(username,password,"Username-Password-Authentication")
                .validateClaims()
                .start(loginCallback)
        }
    }
}