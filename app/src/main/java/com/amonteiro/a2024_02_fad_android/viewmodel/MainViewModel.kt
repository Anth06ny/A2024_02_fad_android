package com.amonteiro.a2024_02_fad_android.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2024_02_fad_android.model.PictureBean
import com.amonteiro.a2024_02_fad_android.model.WeatherAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    val searchText = mutableStateOf("")
    val myList = mutableStateListOf<PictureBean>()
    val runInProgress = mutableStateOf(false)

    //fun filterList() = myList.filter { it.title.contains(searchText.value, true) }

    fun uploadSearchText(newText: String) {
        searchText.value = newText
    }

    fun loadData() {//Simulation de chargement de donnée
        myList.clear()

        //Une tache en cours
        runInProgress.value = true

        viewModelScope.launch(Dispatchers.Default) {

            //Requête
            val listWeather = WeatherAPI.loadWeatherAround(searchText.value)

            val listPicture = listWeather.map { weather ->
                PictureBean(
                    weather.id,
                    weather.weather.getOrNull(0)?.icon ?: "",
                    weather.name,
                    "Il fait ${weather.main.temp}°")
            }
            //Retourner sur le Thread principale
            launch(Dispatchers.Main) {
                //J'ajoute tous les éléments à myList qui est observé
                myList.addAll(listPicture)
                //Tache terminée
                runInProgress.value = false
            }
        }
    }
}