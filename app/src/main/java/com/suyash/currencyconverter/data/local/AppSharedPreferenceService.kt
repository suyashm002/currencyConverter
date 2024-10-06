package com.suyash.currencyconverter.data.local

import android.content.Context
import java.util.concurrent.TimeUnit

class AppSharedPreferenceService(private val context: Context) : SharedPreferenceService {

    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    override fun isMoreThanThirtyMinutes(): Boolean {

        // Get the saved timestamp (default is 0 if not found)
        val savedTimestamp = sharedPreferences.getLong("saved_timestamp", 0L)

        // If no timestamp is found, return true (first-time or reset condition)
        if (savedTimestamp == 0L) {
            return true
        }

        // Get the current time
        val currentTime = System.currentTimeMillis()

        // Calculate the difference in minutes
        val diffInMillis = currentTime - savedTimestamp
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

        // Check if the difference is more than 30 minutes
        return diffInMinutes > 30
    }

    override fun saveTimeStamp() {


        val currentTime = System.currentTimeMillis()
// Create an editor to write data
        val editor = sharedPreferences.edit()

// Put the string value in SharedPreferences
        editor.putLong("saved_timestamp", currentTime)
// Commit or apply the changes
        editor.apply() // Or use editor.commit() if you want synchronous saving
    }
}