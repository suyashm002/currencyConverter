package com.suyash.currencyconverter.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.suyash.currencyconverter.R
import com.suyash.currencyconverter.ui.offlinecurrency.currencyRateScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            currencyRateScreen()
        }
    }
}