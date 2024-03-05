package com.bertan.learnandroidappdevelopment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bertan.learnandroidappdevelopment.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lvTodoList: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var shoppingItems: ArrayList<Pair<String, String>>
    private lateinit var itemAdapter: CustomAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_tutorial -> {
                navController.navigate(R.id.action_mainFragment_to_tutorialFragment)
                true
            }             // handle settings action
            else -> false
        }
    }




}