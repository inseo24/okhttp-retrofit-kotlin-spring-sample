package com.seoin.example.webclient.retrofit.dto

data class GetSupportedCountriesResponse(
    val status: String,
    val data: List<Country>,
)

data class Country(
    val country: String,
)
