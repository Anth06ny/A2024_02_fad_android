package com.amonteiro.a2024_02_fad_android.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.amonteiro.a2024_02_fad_android.model.PictureBean
import com.amonteiro.a2024_02_fad_android.model.pictureList

class MainViewModel : ViewModel() {

    val searchText = mutableStateOf("")
    val myList = mutableStateListOf<PictureBean>()

    //fun filterList() = myList.filter { it.title.contains(searchText.value, true) }

    fun uploadSearchText(newText: String) {
        searchText.value = newText
    }

    fun loadData(forece:Boolean = true) {//Simulation de chargement de donnée


        myList.clear()
        Thread.sleep(1000) //simule temps de la requête
        myList.addAll(pictureList.shuffled()) //Charge la liste en mode mélangé
    }
}