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

    // POST, PUT은 예시 API에 없으므로 예시만 남김
    // @Body : Request Body
    // @Path : Path Variable
    // @Query : Query Parameter
    @POST("/v2/countries")
    fun postSupportedCountries(
        @Body request: GetSupportedStatesResponse,
        @Path("state") state: String,
        @Query("key") key: String,
    ): Call<GetSupportedStatesResponse>

    @PUT("/v2/states")
    fun putSupportedStates(
        @Header("Authorization") authorization: String,
        @Body request: GetSupportedStatesResponse,
        @Query("key") key: String,
    ): Call<GetSupportedStatesResponse>
}
