package com.example.myapplication // Проверьте package!

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import java.util.Scanner

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<WeatherData>()
    val weather: LiveData<WeatherData> = _weather

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val cities = listOf("Moscow", "Irkutsk", "London", "Paris", "New York")
    var selectedCity: String = "Moscow" // Город по умолчанию

    fun loadWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiKey = "d6843ab8ee963f5d372296dfff62aed7" // Ваш ключ
                val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"

                val stream = URL(weatherURL).getContent() as InputStream
                val data = Scanner(stream).nextLine()
                val json = JSONObject(data)

                val cityName = json.getString("name")
                val main = json.getJSONObject("main")
                val temp = main.getDouble("temp")
                val humidity = main.getInt("humidity")

                val weatherArray = json.getJSONArray("weather")
                val weatherObj = weatherArray.getJSONObject(0)
                val description = weatherObj.getString("description")
                val icon = weatherObj.getString("icon")

                val wind = json.getJSONObject("wind")
                val windSpeed = wind.getDouble("speed")
                val windDeg = wind.getInt("deg")

                val result = WeatherData(cityName, temp, description, icon, humidity, windSpeed, windDeg)

                _weather.postValue(result)
                _error.postValue("")

            } catch (e: Exception) {
                Log.e("Weather", "Error", e)
                _error.postValue("Ошибка: ${e.message}")
            }
        }
    }
}