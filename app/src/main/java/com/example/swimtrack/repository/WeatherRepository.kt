package com.example.swimtrack.repository

import com.example.swimtrack.data.remote.WeatherApi
import com.example.swimtrack.data.remote.WeatherResponse

class WeatherRepository(
    private val weatherApi: WeatherApi
) {

    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {

        return weatherApi.getCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )
    }
}