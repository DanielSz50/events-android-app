package com.example.eventapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import com.example.eventapp.data.location.LocationManager
import com.example.eventapp.ui.browse.BrowseFragment
import com.example.eventapp.ui.browse.BrowseViewModel
import com.example.eventapp.ui.home.HomeFragment
import com.example.eventapp.ui.home.HomeViewModel
import com.example.eventapp.ui.saved.SavedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var browseViewModel: BrowseViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isLocationPermitted()) {
            locationManager = LocationManager(this, 10000, 100.0F)
            locationManager.startLocationTracking()
        } else {
            requestLocationPermissions()
        }

        setUpViewModels()
        loadFragment(HomeFragment(this.homeViewModel))

        bottomNav = findViewById(R.id.bottom_nav_view)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment(this.homeViewModel))
                    true
                }
                R.id.navigation_browse -> {
                    loadFragment(BrowseFragment(this.homeViewModel))
                    true
                }
                R.id.navigation_saved -> {
                    loadFragment(SavedFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    private fun setUpViewModels() {
        browseViewModel = ViewModelProvider(this)[BrowseViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun isLocationPermitted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {}
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {} else -> {}
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }
}