package com.rrg.sicpa_test.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.rrg.sicpa_test.R
import com.rrg.sicpa_test.databinding.ActivityMainBinding
import com.rrg.sicpa_test.utils.checkInternetPingGoogle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                setupFragment(destination)
            }
        }

        listenForConnectivity()
        if(!checkInternetPingGoogle()){ // Connectivity manager callback does not detect initial network state.
            showNoInternetBanner(true)
        }
    }

    private fun setupFragment(destination: NavDestination) = binding.apply {
        when (destination.id) {
            R.id.homeFragment -> {
                setToolbarTitle(getString(R.string.new_york_times))
                setToolbarBackAction()
            }
            R.id.articleListFragment -> {
                setToolbarTitle(getString(R.string.search_articles))
                setToolbarBackAction(true)
            }
            R.id.popularArticlesFragment -> {
                setToolbarBackAction(true)
            }
        }
    }

    fun setToolbarTitle(title: String, centerTitle: Boolean = true) {
        binding.appBarLayout.toolbar.apply {
            this.title = title
            isTitleCentered = centerTitle
        }
    }

    private fun setToolbarBackAction(showBackButton: Boolean = false, destination: Int? = null) =
        binding.appBarLayout.toolbar.apply {
            if (showBackButton) {
                setNavigationIcon(R.drawable.ic_arrow_back)
            } else {
                navigationIcon = null
            }
            setNavigationOnClickListener {
                if (destination == null) {
                    navController.navigateUp() // If no destination is set, we do normal navigation up.
                } else {
                    navController.popBackStack(destination, false)
                }
            }
        }

    private fun showNoInternetBanner(showView: Boolean)  = runOnUiThread {
        binding.apply {
            offlineConstraintLayout.isVisible = showView
        }
    }

    private fun listenForConnectivity(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // network available
                Timber.e("INTERNET")
                showNoInternetBanner(false)
                super.onAvailable(network)
            }
            override fun onLost(network: Network) {
                Timber.e("NOOOOO INTERNET")
                // network unavailable / no Internet
                showNoInternetBanner(true)
                super.onLost(network)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }
}