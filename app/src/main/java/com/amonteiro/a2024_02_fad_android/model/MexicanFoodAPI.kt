package com.amonteiro.a2024_02_fad_android.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request


fun main() {
    //Appel 1
    val res =MexicanFoodAPI.loadListOfFood().first()
    println(res)
//    println(res.joinToString("\n") {
//        "-${it.title} : ${it.difficulty}"
//    })

//    //Appel2
//    val detail =MexicanFoodAPI.foodDetail("10")
//    println(detail)
}

object MexicanFoodAPI {

    val gson = Gson()
    val client = OkHttpClient()

    //Charge la liste des recettes avec image et id
    fun foodDetail(id:String): MexicanFoodBean {
        val json = sendGet("/$id")
        return gson.fromJson(json, MexicanFoodBean::class.java)
    }

    //Charge la liste des recettes avec image et id
    fun loadListOfFood(): List<MexicanFoodBean> {
        val json = sendGet("")
        return gson.fromJson(json, Array<MexicanFoodBean>::class.java).toList()
    }


    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        val finalUrl = "https://the-mexican-food-db.p.rapidapi.com$url"

        println("finalUrl : $finalUrl")
        //Création de la requête
        val request = Request.Builder()
            .url(finalUrl)
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
    var difficulty: String,
    var id: String,
    var image: String,
    var title: String,

    var description: String? = null,
    var ingredients: List<String>?= null,
    var method: List<StepBean>?= null,
    var portion: String?= null,
    var time: String?= null
)

data class StepBean(
    @SerializedName("Step 1")
    var step1: String?,
    @SerializedName("Step 2")
    var step2: String?,
    @SerializedName("Step 3")
    var step3 : String?,
    @SerializedName("Step 4")
    var step4 : String?,
    @SerializedName("Step 5")
    var step5 : String?,
    @SerializedName("Step 6")
    var step6 : String?,
    @SerializedName("Step 7")
    var step7 : String?,
)