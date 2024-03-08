package com.bertan.learnandroidappdevelopment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : Fragment(), MenuProvider {
    private lateinit var lvTodoList: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var shoppingItems: ArrayList<Pair<String, String>>
    private lateinit var itemAdapter: CustomAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        lvTodoList = view.findViewById(R.id.lvTodoList)
        fab = view.findViewById(R.id.floatingActionButton)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Load shopping items from SharedPreferences
        shoppingItems = loadShoppingItems()

        itemAdapter = CustomAdapter(requireContext(), shoppingItems)
        lvTodoList.adapter = itemAdapter

        addItem(fab, requireContext())
        deleteItem(lvTodoList, requireContext())

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val mainActivity = activity as MainActivity
        return when (menuItem.itemId) {
            R.id.action_tutorial -> {
                mainActivity.navController.navigate(R.id.action_mainFragment_to_settingsFragment)
                true
            }
            R.id.action_delete -> {
                val alertDialog = AlertDialog.Builder(requireContext()).apply {
                    setTitle("Are you sure about deleting all items?")
                    setPositiveButton("Confirm"){_,_ ->
                        shoppingItems.clear()
                        itemAdapter.notifyDataSetChanged()
                        saveShoppingItems()

                        Toast.makeText(
                            context.applicationContext,
                            "Items deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    setNegativeButton("Cancel"){_,_ ->
                        Toast.makeText(
                            context.applicationContext,
                            "Deletion canceled",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                alertDialog.show()
                true
            }
            else -> false
        }
    }

    private fun loadShoppingItems(): ArrayList<Pair<String, String>> {
        val savedItems = sharedPreferences.getStringSet("shoppingItems", HashSet()) ?: HashSet()
        return ArrayList(savedItems.map { it.split(",") }.map { it[0] to it[1] })
    }

    private fun saveShoppingItems() {
        val editor = sharedPreferences.edit()
        val itemSet = HashSet(shoppingItems.map { "${it.first},${it.second}" })
        editor.putStringSet("shoppingItems", itemSet)
        editor.apply()
    }

    private fun deleteItem(lvTodoList: ListView, context: Context) {
        lvTodoList.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, _, pos, _ ->
                val alertDialog = AlertDialog.Builder(context).apply {
                    setTitle("Are you sure about deleting this item?")
                    setPositiveButton("Delete") { _, _ ->
                        shoppingItems.removeAt(pos)
                        itemAdapter.notifyDataSetChanged()
                        saveShoppingItems() // Save changes after deletion
                        Toast.makeText(
                            context.applicationContext,
                            "Item deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(
                            context.applicationContext,
                            "Deletion canceled",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                alertDialog.show()
                true
            }
    }

    private fun addItem(fab: FloatingActionButton, context: Context) {
        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_custom_layout, null)
            val inputItem = view.findViewById<EditText>(R.id.editText1)
            val inputCount = view.findViewById<EditText>(R.id.editText2)

            val alertDialog = AlertDialog.Builder(context).apply {
                setTitle("Add Items")
                setView(view)
                setPositiveButton("Add") { _, _ ->
                    if (inputItem.text.isNotBlank()) {
                        shoppingItems.add(Pair(inputItem.text.toString().trim(), inputCount.text.toString().trim()))
                        itemAdapter.notifyDataSetChanged()
                        saveShoppingItems() // Save changes after addition
                    }
                }
                setNegativeButton("Close") { _, _ ->
                    Toast.makeText(context.applicationContext, "Closed", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog.show()
        }
    }
}
