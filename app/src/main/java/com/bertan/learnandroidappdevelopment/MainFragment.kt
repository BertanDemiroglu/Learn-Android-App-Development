package com.bertan.learnandroidappdevelopment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onResume() {
        super.onResume()

        view?.postDelayed({
            val mainActivity = activity as MainActivity
            mainActivity.navController.navigate(R.id.action_mainFragment_to_tutorialFragment)
        }, 1_500)
    }
}