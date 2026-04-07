package com.zhanatpaeva.weatherdashboard02.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhanatpaeva.weatherdashboard02.data.WeatherData
import com.zhanatpaeva.weatherdashboard02.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class WeatherViewModel : ViewModel() {
    private val repository  = WeatherRepository()
    private val _weatherState = MutableStateFlow(WeatherData())
    val weatherState : StateFlow<WeatherData> = _weatherState.asStateFlow()
    init {
        loadWeatherData()
    }
    fun loadWeatherData(){
        viewModelScope.launch {
            _weatherState.value = _weatherState.value.copy(
                isLoading = true ,
                error = null,
                loadingProgress = "запуск згрузки..."
            )

//            try {
//
//                _weatherState.value = _weatherState.value.copy(
//                    loadingProgress = "загружаем температуру влажность скорость ветра"
//                )
////                val temperatureDeferred = async { repository.fetchTemperature() }
//                val humidityDeferred = async { repository.fetchHumidity() }
//                val windSpeedDeferred = async { repository.fetchWindSpeed() }
//                val temperature = temperatureDeferred.await()
//                val humidity = humidityDeferred.await()
//                val windSpeed = windSpeedDeferred.await()
//                _weatherState.value = WeatherData(
//                    temperature = temperature ,
//                    humidity = humidity ,
//                    windSpeed = windSpeed ,
//                    isLoading = false ,
//                    error = null,
//                    loadingProgress =  "зашрузка завершена"
//                )
//                val temperature = repository.fetchTemperature()
//                _weatherState.value = _weatherState.value.copy(temperature = temperature)
//                val humidity = repository.fetchHumidity()
//                _weatherState.value = _weatherState.value.copy(humidity = humidity)
//                val windSpeed = repository.fetchWindSpeed()
//                _weatherState.value = _weatherState.value.copy(windSpeed = windSpeed)
//                _weatherState.value = _weatherState.value.copy(isLoading = false)
            try {
                coroutineScope { // Создаём scope, который НЕ отменяет родителя при ошибке←
                    val tempDeferred = async { repository.fetchTemperature() }
                    val humDeferred = async { repository.fetchHumidity() }
                    val windDeferred = async { repository.fetchWindSpeed() }
                    val temperature = tempDeferred.await()
                    val humidity = humDeferred.await()
                    val windSpeed = windDeferred.await()
                    _weatherState.value = WeatherData(
                        temperature = temperature,
                        humidity = humidity,
                        windSpeed = windSpeed,
                        isLoading = false,
                        error = null,
                        loadingProgress = "Загрузка завершена!"
                    )
                }
            } catch (e: Exception) {
                _weatherState.value = _weatherState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки: ${e.message}",
                    loadingProgress = ""
                )}
//                            catch (e: Exception){
//                _weatherState.value = _weatherState.value.copy(
//                    isLoading = false ,
//                    error = "ошибка загрузаки ${e.message
//                    }",
//                    loadingProgress = ""
//                )
//            }


        }
    }
    fun toggleErrorSimulation(){
        repository.toggleErrorSimulation()
    }
}