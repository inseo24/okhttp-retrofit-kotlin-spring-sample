package com.seoin.example.webclient.retrofit

import com.seoin.example.webclient.retrofit.client.WeatherApiClient
import com.seoin.example.webclient.retrofit.dto.GetSupportedCountriesResponse
import com.seoin.example.webclient.retrofit.dto.GetSupportedStatesResponse
import com.seoin.example.webclient.retrofit.util.RetrofitUtils.getBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WeatherService(
    @Value("\${weather.api.key}")
    private val apiKey: String,
    val weatherApiClient: WeatherApiClient,
) {

    fun getSupportedCountries(): GetSupportedCountriesResponse {
        return weatherApiClient.getSupportedCountries(key = apiKey)
            .execute()
            .getBody()
    }

    fun getSupportedStates(country: String): GetSupportedStatesResponse {
        return weatherApiClient.getSupportedStates(country, key = apiKey)
            .execute()
            .getBody()
    }
}
