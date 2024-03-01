package com.bertan.learnandroidappdevelopment

import android.os.Bundle
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bertan.learnandroidappdevelopment.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lvTodoList: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var shoppingItems: ArrayList<Pair<String, String>>
    private lateinit var itemAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lvTodoList = findViewById(R.id.lvTodoList)
        fab = findViewById(R.id.floatingActionButton)
        shoppingItems = ArrayList()
        itemAdapter = CustomAdapter(this, shoppingItems)
        lvTodoList.adapter = itemAdapter

        val selectedItems = mutableListOf<Pair<String, String>>()

        lvTodoList.setOnItemClickListener { _, _, position, _ ->
            val item = itemAdapter.getItem(position)
            item?.let {
                if (lvTodoList.isItemChecked(position)) {
                    selectedItems.add(it)
                } else {
                    selectedItems.remove(it)
                }
            }
        }

        val b = findViewById<ImageButton>(R.id.tutorialButton)

        deleteItem(lvTodoList)

        addItem(fab)
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
                        shoppingItems.add(Pair(inputItem.text.toString(), inputCount.text.toString()))
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
}

