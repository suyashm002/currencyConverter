package com.suyash.currencyconverter.data.repository


import com.suyash.currencyconverter.data.api.NetworkService
import com.suyash.currencyconverter.data.local.DatabaseService
import com.suyash.currencyconverter.data.local.SharedPreferenceService
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.data.model.ApiResponse
import com.suyash.currencyconverter.data.model.toRateEntity
import com.suyash.currencyconverter.utils.AppConstant.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After


import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
 import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
 import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest

import org.mockito.kotlin.*
import org.junit.Assert.assertEquals

class OfflineCurrencyRateRepositoryTest {

    private lateinit var networkService: NetworkService
    private lateinit var databaseService: DatabaseService
    private lateinit var sharedPreferenceService: SharedPreferenceService
    private lateinit var repository: OfflineCurrencyRateRepository

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        // Mock the dependencies
        networkService = mock()
        databaseService = mock()
        sharedPreferenceService = mock()

        // Set the repository with the mocked services
        repository = OfflineCurrencyRateRepository(
            networkService,
            databaseService,
            sharedPreferenceService
        )

        // Set the main dispatcher for tests
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getCurrencies retrieves data from network when more than 30 minutes have passed`() = runTest {
        // Arrange: Set up mock responses
        val apiResponse = ApiResponse(
            timestamp = "1234567890",
            base = "USD",
            rates = hashMapOf("EUR" to 0.85f, "JPY" to 110.0f)
        )
        val rate = Rate(
            base = "USD",
            currencyRates = apiResponse.rates
        )

        // Mock behavior for shared preferences to indicate 30 minutes have passed
        whenever(sharedPreferenceService.isMoreThanThirtyMinutes()).thenReturn(true)

        // Mock network response
        whenever(networkService.getCurrencies(API_KEY)).thenReturn(apiResponse)

        // Mock database insertion
        doNothing().whenever(databaseService).deleteAllAndInsertAll(rate)

        // Mock database response
        whenever(databaseService.getApiResponse()).thenReturn(flowOf(rate))

        // Act: Invoke the function
        val result = repository.getCurrencies().toList()

        // Assert: Verify the flow behaves as expected
        assertEquals(1, result.size)
        assertEquals("USD", result[0].base)
        assertEquals(0.85f, result[0].currencyRates?.get("EUR"))
        assertEquals(110.0f, result[0].currencyRates?.get("JPY"))

        // Verify that network and database operations were called
        verify(networkService).getCurrencies(API_KEY)
        verify(databaseService).deleteAllAndInsertAll(rate)
    }

    @Test
    fun `getCurrencies retrieves data from database when less than 30 minutes have passed`() = runTest {
        // Arrange: Set up mock responses
        val rate = Rate(
            base = "USD",
            currencyRates = mapOf("EUR" to 0.85f, "JPY" to 110.0f)
        )

        // Mock behavior for shared preferences to indicate less than 30 minutes have passed
        whenever(sharedPreferenceService.isMoreThanThirtyMinutes()).thenReturn(false)

        // Mock database response
        whenever(databaseService.getApiResponse()).thenReturn(flowOf(rate))

        // Act: Invoke the function
        val result = repository.getCurrencies().toList()

        // Assert: Verify the flow behaves as expected
        assertEquals(1, result.size)
        assertEquals("USD", result[0].base)
        assertEquals(0.85f, result[0].currencyRates?.get("EUR"))
        assertEquals(110.0f, result[0].currencyRates?.get("JPY"))

        // Verify that network service was not called
        verify(networkService, never()).getCurrencies(any())
        verify(databaseService, never()).deleteAllAndInsertAll(any())
    }

    @Test
    fun `getCurrenciesDirectlyFromDB retrieves data from database`() = runTest {
        // Arrange: Set up mock responses
        val rate = Rate(
            base = "USD",
            currencyRates = mapOf("EUR" to 0.85f, "JPY" to 110.0f)
        )

        // Mock database response
        whenever(databaseService.getApiResponse()).thenReturn(flowOf(rate))

        // Act: Invoke the function
        val result = repository.getCurrenciesDirectlyFromDB().toList()

        // Assert: Verify the flow behaves as expected
        assertEquals(1, result.size)
        assertEquals("USD", result[0].base)
        assertEquals(0.85f, result[0].currencyRates?.get("EUR"))
        assertEquals(110.0f, result[0].currencyRates?.get("JPY"))

        // Verify that database service was called
        verify(databaseService).getApiResponse()
    }
}
