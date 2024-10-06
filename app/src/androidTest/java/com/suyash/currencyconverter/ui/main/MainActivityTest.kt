package com.suyash.currencyconverter.ui.main

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.suyash.currencyconverter.ui.offlinecurrency.currencyRateScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var composeTestRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun init() {
        hiltRule.inject()
    }
    @Test
    fun testCurrencyConverterInitialUI() {
        // Launch the MainActivity
        composeTestRule.setContent {
            currencyRateScreen()
        }

        // Check if the "Enter Amount" TextField is displayed
        composeTestRule.onNodeWithText("Enter Amount").assertExists()

        // Check if the dropdown for currency selection is displayed
        composeTestRule.onNodeWithText("USD").assertExists() // Assuming "USD" is the default currency
    }
}
