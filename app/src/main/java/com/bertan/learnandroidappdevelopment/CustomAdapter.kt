package com.bertan.learnandroidappdevelopment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.lang.RuntimeException

/**
 * Custom adapter which allows a AlertDialog with two inputs(One main item and a sub item) using
 * [android.R.layout.simple_list_item_2]
 */
class CustomAdapter(context: Context, items: List<Pair<String, String>>) :
    ArrayAdapter<Pair<String, String>>(context, android.R.layout.simple_list_item_2, items) {

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null)
        }

        val item = getItem(position)
        if(item != null && view != null){
            view.findViewById<TextView>(android.R.id.text1)?.text = item.first
            view.findViewById<TextView>(android.R.id.text2)?.text = item.second
            return view
        } else {
            throw RuntimeException()
        }
    }
}