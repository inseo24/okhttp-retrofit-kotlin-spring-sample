package com.seoin.example.webclient.retrofit.config

import com.seoin.example.webclient.retrofit.client.WeatherApiClient
import com.seoin.example.webclient.retrofit.util.createRetrofit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URL

@Configuration
class RetrofitConfig {

    companion object {
        const val BASE_URL = "http://api.airvisual.com"
    }

    @Bean
    fun weatherApiClient() = URL(BASE_URL)
        .createRetrofit()
        .create(WeatherApiClient::class.java)
}
