package com.amonteiro.a2024_02_fad_android.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Method

fun main() {

    var res = (MexicanFoodAPI.loadFood("4"))
    println(res)

}

object MexicanFoodAPI {

    val gson = Gson()
    val client = OkHttpClient()

    fun loadFood(id: String): MexicanFoodBean {
        //Eventuel contrôle
        //Réaliser la requête.
        val json: String = sendGet("/$id")

        //Parser le JSON avec le bon bean et GSON
        val data = gson.fromJson(json, MexicanFoodBean::class.java)

        //Eventuel contrôle ou extraction de données

        //Retourner la donnée
        return data
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder()
            .url("https://the-mexican-food-db.p.rapidapi.com$url")
            .get()
            .addHeader("X-RapidAPI-Key", "93329c7cf9msha136bd696cd1040p10a1dejsnbc52cdb0746e")
            .addHeader("X-RapidAPI-Host", "the-mexican-food-db.p.rapidapi.com")
            .build()

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

data class MexicanFoodBean(
    var description: String,
    var difficulty: String,
    var id: String,
    var image: String,
    var ingredients: List<String>,
    var method: List<MethodBean>,
    var portion: String,
    var time: String,
    var title: String
)

data class MethodBean(
    @SerializedName("Step 1")
    var step1: String,
    @SerializedName("Step 2")
    var step2: String,
    @SerializedName("Step 3")
    var step3: String,
    @SerializedName("Step 4")
    var step4: String,
    @SerializedName("Step 5")
    var step5: String,
    @SerializedName("Step 6")
    var step6: String
)