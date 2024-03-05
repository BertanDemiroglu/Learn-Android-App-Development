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

    private fun deleteItem(lvTodoList: ListView){
        lvTodoList.onItemLongClickListener = OnItemLongClickListener { _, _, pos, _ ->
            val alertDialog = AlertDialog.Builder(this).apply {
                setTitle("Are you sure about deleting this item?")
                setPositiveButton("Delete"){ _, _ ->
                    shoppingItems.removeAt(pos)
                    itemAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Item deleted!", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel"){ _, _ ->
                    Toast.makeText(applicationContext, "Deletion canceled", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog.show()
            true
        }
    }

    private fun addItem(fab: FloatingActionButton){
        fab.setOnClickListener{
            val view = layoutInflater.inflate(R.layout.dialog_custom_layout, null)
            val inputItem = view.findViewById<EditText>(R.id.editText1)
            val inputCount = view.findViewById<EditText>(R.id.editText2)

            val alertDialog = AlertDialog.Builder(this).apply {
                setTitle("Add Items")
                setView(view)
                setPositiveButton("Add"){ _, _ ->
                    if(inputItem.text.isNotBlank()){
                        shoppingItems.add(Pair(inputItem.text.toString().trim(), inputCount.text.toString().trim()))
                        itemAdapter.notifyDataSetChanged()
                    }
                }
                setNegativeButton("Close"){ _, _ ->
                    Toast.makeText(applicationContext, "Closed", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog.show()
        }
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