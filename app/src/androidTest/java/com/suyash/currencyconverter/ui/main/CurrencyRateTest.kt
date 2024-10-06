package com.suyash.currencyconverter.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.ui.base.UiState
import com.suyash.currencyconverter.ui.offlinecurrency.OfflineCurrencyScreen
import org.junit.Rule
import org.junit.Test


class CurrencyRateTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()




    @Test
    fun testCurrencyRateScreen_SuccessState()  {
        // Mock data for the success state
        val mockRate = mockRates
        val mockUiState = UiState.Success(mockRate)

        // Set the content for the test
        composeTestRule.setContent {
            // Provide the mocked uiState to the CurrencyRateScreen
            CurrencyRateScreenTest(mockUiState)
        }

        // Add assertions to verify UI elements
        composeTestRule.onNodeWithText("USD").assertIsDisplayed() // Assuming you expect USD to be displayed
    }

    @Composable
    fun CurrencyRateScreenTest(uiState: UiState<Rate>) {
        // Directly call the OfflineCurrencyScreen with the provided state
        OfflineCurrencyScreen(uiState)
    }

    // Create a mockRates instance for testing
    private val mockRates = Rate(
        base = "USD",
        currencyRates = mapOf(
            "EUR" to 0.85f,
            "JPY" to 110.0f,
            "GBP" to 0.75f
        )
    )
}