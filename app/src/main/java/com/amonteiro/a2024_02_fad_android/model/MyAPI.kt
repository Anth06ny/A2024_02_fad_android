package com.amonteiro.a2024_02_fad_android.model

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

fun main() {

   val res = MyAPI.increment(StudentBean("Toto",6))
    println("res=$res")

}

object MyAPI {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val gson = Gson()
    val client = OkHttpClient()

    const val URL_SERVER = "http://localhost:8080"





    fun increment(studentBean: StudentBean ): StudentBean {
        val json = sendPost("$URL_SERVER/increment", studentBean)
        return gson.fromJson(json, StudentBean::class.java)
    }

    fun boulangerie(nbCroissant:Int = 0, nbSandwitch : Int = 0 ): Double {
        val res = sendGet("$URL_SERVER/boulangerie?nbCroissant=$nbCroissant&nbSandwich=$nbSandwitch")
        return res.toDouble()
    }

    fun loadStudent(name:String, note:Int): StudentBean {

        val json = sendGet("$URL_SERVER/createStudent?name=$name&notation=$note")
        return gson.fromJson(json, StudentBean::class.java)
    }

    fun loadStudent(): StudentBean {

       val json = sendGet("$URL_SERVER/getStudent")
       return gson.fromJson(json, StudentBean::class.java)
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


    //version qui prend n'importe quel objet et le convertira en JSON
    fun sendPost(url: String, toSend: Any?): String {
        println("url : $url")

        //Convertir en JSON
        val json = gson.toJson(toSend)

        //Corps de la requête
        val body = json.toRequestBody(MEDIA_TYPE_JSON)

        //Création de la requête
        val request = Request.Builder().url(url).post(body).build()

        //Execution de la requête
        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
}

data class MessageBean(var pseudo: String, var message: String)