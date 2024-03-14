package com.amonteiro.a2024_02_fad_android.model

import java.util.Random

fun main() {

    var u1 = User
    u1.name = "toto"
    var u2 = User
    u2.name = "tata"

    println(u1)
    println(u2)

}


const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""

data class PictureBean(val id:Int, val url: String, val title: String, val longText: String)

//jeu de donnée
val pictureList = arrayListOf(PictureBean(1, "https://picsum.photos/200", "ABCD", LONG_TEXT),
    PictureBean(2, "https://picsum.photos/201", "BCDE", LONG_TEXT),
    PictureBean(3, "https://picsum.photos/202", "CDEF", LONG_TEXT),
    PictureBean(4, "https://picsum.photos/203", "EFGH", LONG_TEXT)
)

data class StudentBean(var name:String, var note:Int)

object User {
    var name: String = ""
}

data class CarBean(var marque: String, var model: String) {
    var color = ""

    fun println() = println("$marque $model $color")

}


class RandomName {
    private val list = arrayListOf("Toto", "Bob", "Titi")
    private var oldValue = ""

    fun add(name: String?) = if (!name.isNullOrBlank() && name !in list) list.add(name) else false

    fun next() = list.random()

    fun nextDiff(): String {
        var newValue = next()
        while(newValue == oldValue){
            newValue = next()
        }
        oldValue = newValue
        return newValue
    }

    fun nextDiffV2() = list.filter {
        it != oldValue }.random().also { oldValue = it }


    fun next2() = Pair(nextDiff(), nextDiff())

}

class ThermometerBean(val min: Int, val max: Int, value: Int) {
    var value: Int = value.coerceIn(min, max)
        set(newValue) {
            field = newValue.coerceIn(min, max)
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerBean(-30, 50, 0)
        fun getFahrenheitThermometer() = ThermometerBean(20, 120, 32)
    }
}


class PrintRandomIntBean(val max: Int) {
    private val random = Random()

    init {

        repeat(3) {
            println(random.nextInt(max))
        }
    }

    constructor() : this(100) {
        println(random.nextInt(max))
    }
}

class HouseBean(var color: String, length: Int, width: Int) {
    var area = width * length
}


