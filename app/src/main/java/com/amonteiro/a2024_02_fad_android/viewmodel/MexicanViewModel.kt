package com.amonteiro.a2024_02_fad_android.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2024_02_fad_android.model.MexicanFoodAPI
import com.amonteiro.a2024_02_fad_android.model.MexicanFoodBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MexicanViewModel : ViewModel() {

    var list = mutableStateListOf<MexicanFoodBean>()
    var errorMessage = mutableStateOf("")
    var selectMexicanFood :MexicanFoodBean? = null



    fun loadData() {

        errorMessage.value = ""

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val newData = MexicanFoodAPI.loadListOfFood()
                launch(Dispatchers.Main) {
                    list.addAll(newData)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    errorMessage.value = "Erreur : ${e.message}"
                }
            }
        }
    }

    fun loadDetail() {

        errorMessage.value = ""

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val selectedValue = selectMexicanFood
                if(selectedValue != null) {
                    val newData = MexicanFoodAPI.foodDetail(selectedValue.id)
                    launch(Dispatchers.Main) {
                        //Je viens cher'cher la position de celui qui est séléctionné
                        val position = list.indexOfFirst { it.id == selectedValue.id }
                        //Je le remplace dans la liste
                        list[position] = newData
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    errorMessage.value = "Erreur : ${e.message}"
                }
            }
        }
    }

    fun loadFakeData() {
        list.add(
            MexicanFoodBean(difficulty = "Easy", id = "1", image = "https://apipics.s3.amazonaws.com/mexican_api/1.jpg", title = "Pressure cooker refried beans")
        )

        list.add(
            MexicanFoodBean(difficulty = "Medium", id = "2", image = "https://apipics.s3.amazonaws.com/mexican_api/2.jpg", title = "Pressure")
        )

        list.add(
            MexicanFoodBean(difficulty = "Hard", id = "3", image = "https://apipics.s3.amazonaws.com/mexican_api/3.jpg", title = "cooker")
        )

    }
}