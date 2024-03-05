package com.bertan.learnandroidappdevelopment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bertan.learnandroidappdevelopment.databinding.FragmentMainBinding


class MainFragment : Fragment() , MenuProvider{
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //setSupportActionBar(binding.toolbar)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_tutorial -> {
                findNavController().navigate(R.id.action_mainFragment_to_tutorialFragment)
                true
            }             // handle settings action
            else -> false
        }
    }
}