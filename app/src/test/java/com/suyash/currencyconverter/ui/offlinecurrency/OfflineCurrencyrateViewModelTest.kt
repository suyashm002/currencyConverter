package com.suyash.currencyconverter.ui.offlinecurrency

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.suyash.currencyconverter.data.api.NetworkService
import com.suyash.currencyconverter.data.local.DatabaseService
import com.suyash.currencyconverter.data.local.SharedPreferenceService
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.data.repository.OfflineCurrencyRateRepository
import com.suyash.currencyconverter.ui.base.UiState
import com.suyash.currencyconverter.utils.NetworkHelper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class OfflineCurrencyrateViewModelTest {

    // Use this rule to make LiveData and StateFlow execute synchronously in tests
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Dispatcher for testing coroutines
    private val testDispatcher = StandardTestDispatcher()

    // Mocked dependencies
    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var offlineCurrencyRateRepository: OfflineCurrencyRateRepository

    @Mock
    lateinit var sharedPreferenceService: SharedPreferenceService

    @Mock
    lateinit var databaseService: DatabaseService

    @Mock
    lateinit var networkService: NetworkService

    // ViewModel under test
    private lateinit var viewModel: OfflineCurrencyrateViewModel

    @BeforeEach
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this)

        // Set main dispatcher for testing
        Dispatchers.setMain(testDispatcher)

        // Initialize ViewModel with mocked dependencies
        viewModel = OfflineCurrencyrateViewModel(networkHelper, offlineCurrencyRateRepository)
    }


    @Test
    fun `test fetchCurrency when network is connected`() = runTest {

        // Given: network is connected
        whenever(networkHelper.isNetworkConnected()).thenReturn(true)

        // Given: mock repository response
        val rateMock = Rate(base = "USD", currencyRates = mapOf("EUR" to 0.84f))
        whenever(offlineCurrencyRateRepository.getCurrencies()).thenReturn(flowOf(rateMock))

        // When: creating the ViewModel
        viewModel = OfflineCurrencyrateViewModel(networkHelper, offlineCurrencyRateRepository)

        // Then: collect the `uiState` flow to check state changes
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())  // Initial state should be Loading
            assertEquals(
                UiState.Success(rateMock),
                awaitItem()
            )  // Next state should be Success with mock data
            cancelAndIgnoreRemainingEvents()  // Cancel flow collection
        }
    }

    @Test
    fun `test fetchCurrencyRatesDirectlyFromDB when network is disconnected`() = runTest {
        // Given: network is disconnected
        whenever(networkHelper.isNetworkConnected()).thenReturn(false)

        // Given: mock repository response
        val rateMock = Rate(base = "USD", currencyRates = mapOf("EUR" to 0.84f))
        whenever(offlineCurrencyRateRepository.getCurrenciesDirectlyFromDB()).thenReturn(
            flowOf(
                rateMock
            )
        )

        // When: creating the ViewModel
        viewModel = OfflineCurrencyrateViewModel(networkHelper, offlineCurrencyRateRepository)

        // Then: collect the `uiState` flow to check state changes
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())  // Initial state should be Loading
            assertEquals(
                UiState.Success(rateMock),
                awaitItem()
            )  // Next state should be Success with mock data
            cancelAndIgnoreRemainingEvents()  // Cancel flow collection
        }
    }

    @Test
    fun `test fetchCurrency when network is connected and fetch fails`() = runTest {
        // Given: network is connected
        whenever(networkHelper.isNetworkConnected()).thenReturn(true)

        // Given: mock repository throws an exception
        val errorMessage = "Network error"
        whenever(offlineCurrencyRateRepository.getCurrencies()).thenReturn(flow {
            throw RuntimeException(errorMessage)
        })

        // When: creating the ViewModel
        viewModel = OfflineCurrencyrateViewModel(networkHelper, offlineCurrencyRateRepository)

        // Then: collect the `uiState` flow to check state changes
        viewModel.uiState.test(timeout = 5.seconds) { // Increase the timeout to 5 seconds
            assertEquals(UiState.Loading, awaitItem())  // Initial state should be Loading
            val errorState = awaitItem()  // Expecting the Error state to be emitted

            // Assert that the error state is emitted and has the correct message
            assertTrue(errorState is UiState.Error)
            assertEquals("java.lang.RuntimeException: $errorMessage", (errorState as UiState.Error).message)

            cancelAndIgnoreRemainingEvents()  // Cancel flow collection
        }
    }
}
