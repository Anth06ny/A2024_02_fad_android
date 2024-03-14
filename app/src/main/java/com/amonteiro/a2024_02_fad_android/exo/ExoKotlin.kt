package com.amonteiro.a2024_02_fad_android.exo

import com.amonteiro.a2024_02_fad_android.model.WeatherAPI


fun main() {
    //Lazy loading + une seule instanciation
    var html = WeatherAPI.sendGet("https://api.openweathermap.org/data/2.5/weather?q=Toulouse&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")
    println(html)
}



fun boulangerie(nbCroissant : Int = 0, nbSandwitch : Int= 0, nbBaguette : Int= 0) : Double
    = nbCroissant * PRIX_CROISSANT + nbSandwitch * PRIX_SANDWITCH + nbBaguette * PRIX_BAGUETTE


fun scanText(question:String): String {
    print(question)
    return readlnOrNull() ?: "-"
}

fun scanNumber(question:String) = scanText(question).toIntOrNull() ?: 0


fun min(a: Int, b: Int, c: Int): Int = if (a < b && a < c) a else if (b < a && b < c) b else c

fun pair(c:Int): Boolean = c %2 == 0

fun myPrint(text:String): Unit = println("#$text#")

