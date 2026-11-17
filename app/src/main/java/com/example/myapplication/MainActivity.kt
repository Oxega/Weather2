package com.example.myapplication // Проверьте package!

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Настройка Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, viewModel.cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.adapter = adapter

        // Обработка выбора города
        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedCity = viewModel.cities[position]
                viewModel.loadWeather(viewModel.selectedCity) // Загружаем при выборе
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Первичная загрузка
        viewModel.loadWeather(viewModel.selectedCity)
    }
}