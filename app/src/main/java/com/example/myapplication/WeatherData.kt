package com.example.myapplication // Убедитесь, что package совпадает с вашим!

data class WeatherData(
    val city: String = "Загрузка...",
    val temp: Double = 0.0,
    val description: String = "",
    val icon: String = "",
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val windDeg: Int = 0
) {
    // Готовые строки для Data Binding
    val tempString: String get() = String.format("%.1f°C", temp)
    val humidityString: String get() = "Влажность: $humidity%"
    val windString: String get() = String.format("Ветер: %.1f м/с", windSpeed)
}