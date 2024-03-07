package com.bertan.learnandroidappdevelopment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bertan.learnandroidappdevelopment.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}