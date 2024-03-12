package com.bertan.learnandroidappdevelopment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

/**
 * This fragment holds all settings for the app.
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        //saving settings with SharedPreferences
        val darkModePreference = findPreference<SwitchPreference>("dark_mode")
        val sharedPreferences = activity?.getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val nightMode = sharedPreferences?.getBoolean("night", true)

        if(nightMode == true){
            darkModePreference?.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        darkModePreference?.setOnPreferenceChangeListener { _, isDarkModeEnabled ->
            if (isDarkModeEnabled as Boolean) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor?.apply {
                    putBoolean("night", true)
                    apply()
                }
            } else {
                // Disable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor?.apply {
                    putBoolean("night", false)
                    apply()
                }
            }
            true
        }
    }
}