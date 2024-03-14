package com.amonteiro.a2024_02_fad_android.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {

//    var res = (WeatherAPI.loadWeather("Nice"))
//    println(res)



}

object WeatherAPI {

    val gson = Gson()
    val client = OkHttpClient()

    fun loadWeather(cityName: String): WeatherBean {
        //Eventuel contrôle
        //Réaliser la requête.
        val json: String = sendGet("https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")

        //Parser le JSON avec le bon bean et GSON
        val data: WeatherBean = gson.fromJson(json, WeatherBean::class.java)

        //Eventuel contrôle ou extraction de données

        //Retourner la donnée
        return data
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()

        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
}


data class WeatherBean(var main: TempBean, var name: String, var wind: WindBean, var toto: String)
data class TempBean(var temp: Double)
data class WindBean(var speed: Double)

