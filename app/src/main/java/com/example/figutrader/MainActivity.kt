package com.example.figutrader

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.figutrader.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var account = Auth0("OisvgmujDA8VxbbBdvbI1h7itTe2OgjD", "dev-1v0y0xnrlbzoiled.us.auth0.com")
    var cachedCredentials: Credentials? = null
    var cachedUserProfile: UserProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userProfileCallBack = object : Callback<UserProfile, AuthenticationException> {
            override fun onFailure(exception: AuthenticationException) {
                Log.e("userProfileCallBack",exception.getDescription())
            }

            override fun onSuccess(userProfile: UserProfile) {
                cachedUserProfile = userProfile
                Log.i("userProfileCallBack  Activity", userProfile.getId() + " - mail" + userProfile.email + " - nombre" + userProfile.nickname)
            }
        }

        setSupportActionBar(binding.appBarMain.toolbar)

        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()


        val bundle = Bundle()
        bundle.putString("username", cachedUserProfile?.name)
        bundle.putString("usermail", cachedUserProfile?.name)

       // cachedUserProfile?.name?.let { Log.i("Username en activity", it) }
       // menuPrincipalFragment.setUsername(cachedUserProfile?.name);


        //mFragmentTransaction.add(binding.navView,menuPrincipalFragment).commit();

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_menu_principal, R.id.nav_album, R.id.nav_scan
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}