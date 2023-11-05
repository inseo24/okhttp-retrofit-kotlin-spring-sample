package com.seoin.example.webclient.retrofit.client

import com.seoin.example.webclient.retrofit.dto.GetSupportedCountriesResponse
import com.seoin.example.webclient.retrofit.dto.GetSupportedStatesResponse
import retrofit2.Call
import retrofit2.http.*

interface WeatherApiClient {

    @GET("/v2/states")
    fun getSupportedStates(
        @Query("country") country: String,
        @Query("key") key: String,
    ): Call<GetSupportedStatesResponse>

    @GET("/v2/countries")
    fun getSupportedCountries(
        @Query("key") key: String,
    ): Call<GetSupportedCountriesResponse>
}
