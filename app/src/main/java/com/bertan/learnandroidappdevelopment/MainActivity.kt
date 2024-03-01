package com.bertan.learnandroidappdevelopment

import android.os.Bundle
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.EditText
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
        deleteItem(lvTodoList)

        addItem(fab)
        print(3)
    }

    private fun deleteItem(lvTodoList: ListView){
        lvTodoList.onItemLongClickListener = OnItemLongClickListener { _, _, pos, _ ->
            shoppingItems.removeAt(pos)
            itemAdapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "Item deleted!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun addItem(fab: FloatingActionButton){
        fab.setOnClickListener{
            val view = layoutInflater.inflate(R.layout.dialog_custom_layout, null)
            val inputItem = view.findViewById<EditText>(R.id.editText1)
            val inputCount = view.findViewById<EditText>(R.id.editText2)


            val builder = AlertDialog.Builder(this).apply {
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

            builder.show()
        }
    }
}

