package com.seoin.example.webclient.retrofit

import com.seoin.example.webclient.retrofit.client.WeatherApiClient
import com.seoin.example.webclient.retrofit.dto.GetSupportedCountriesResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import retrofit2.Call
import retrofit2.Response

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetrofitTest(
    @Autowired private val weatherApiClient: WeatherApiClient,
) {

    @Value("\${weather.api.key}")
    private val apiKey: String = ""

    private lateinit var weatherService: WeatherService

    @BeforeEach
    fun setUp() {
        weatherService = WeatherService(apiKey, weatherApiClient)
    }

    @Test
    fun testGetSupportedCountries() {
        val response = weatherService.getSupportedCountries()

        assertNotNull(response)
        assertEquals("success", response.status)
        assertNotNull(response.data)
        assertFalse(response.data.isEmpty())

        response.data.forEach { data ->
            assertNotNull(data.country)
        }
    }

    @Test
    fun testGetSupportedCountriesFailure() {
        val mockApiClient = mock(WeatherApiClient::class.java)
        val call = mock(Call::class.java) as Call<GetSupportedCountriesResponse>

        val response = Response.error<GetSupportedCountriesResponse>(400, "API call failed".toResponseBody(null))

        `when`(call.execute()).thenReturn(response)
        `when`(mockApiClient.getSupportedCountries(key = apiKey)).thenReturn(call)

        val mockService = WeatherService(apiKey, mockApiClient)

        assertThrows<RuntimeException> {
            mockService.getSupportedCountries()
        }
    }

    @Test
    fun testGetSupportedStates() {
        val response = weatherService.getSupportedStates("France")

        assertNotNull(response)
        assertEquals("success", response.status)
        assertNotNull(response.data)
        assertFalse(response.data.isEmpty())

        response.data.forEach { data ->
            assertNotNull(data.state)
        }
    }
}
