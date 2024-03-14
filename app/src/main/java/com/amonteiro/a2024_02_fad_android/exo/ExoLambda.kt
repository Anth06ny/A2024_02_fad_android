package com.amonteiro.a2024_02_fad_android.exo

fun main() {
    exo2()
}

data class UserBean(var name:String, var old:Int)

fun exo2(){
    val compareUsersByName :(UserBean, UserBean)->UserBean = {u1, u2 -> if( u1.name.lowercase() <= u2.name.lowercase()) u1 else u2 }
    val compareUsersByOld :(UserBean, UserBean)->UserBean = {u1, u2 -> if( u1.old <= u2.old) u1 else u2 }

    val u1 = UserBean("Bob", 19)
    val u2 = UserBean("Toto", 45)
    val u3 = UserBean("Charles", 26)

    println(compareUsers(u1, u2, u3, compareUsersByName)) // UserBean(name=Bob old=19)
    println(compareUsers(u1, u2, u3, compareUsersByOld)) // UserBean(name=Toto old=45)
    println(compareUsers(u1, u2, u3 ){u1, u2 ->
        if( Math.abs(30 - u1.old) < Math.abs(30 - u2.old)) u1 else u2
    }) // UserBean(name=Toto old=45)
}

fun compareUsers(
    u1 : UserBean,
    u2  :UserBean,
    u3 : UserBean,
    comparator : (UserBean, UserBean)->UserBean): UserBean {

    val response =  comparator(comparator(u1, u2), u3)
    return response
}

fun exo1(){
    val lower : (String) -> Unit = { it: String-> println(it.lowercase()) }
    val lower2 = { it: String-> println(it.lowercase()) }
    val lower3 : (String) -> Unit = { it-> println(it.lowercase()) }
    val lower4 : (String) -> Unit = { println(it.lowercase()) }

    val hour : (Int) -> Int = {it/60}
    val max : (Int, Int) -> Int = {a, b -> Math.max(a,b)}
    val reverse  : (String) -> String = {it.reversed()}

    val minToMinHour : (Int)-> Pair<Int, Int> = {Pair(it/60, it%60)}
    val minToMinHour2 : (Int?)-> Pair<Int, Int>? = {if(it!= null) Pair(it/60, it%60) else null}
    val minToMinHour3 : ((Int?)-> Pair<Int, Int>?)? = null


    lower("Un Texte Avec Des Majuscules")
    println(hour(123))
    println(max(12, 5))
    println(reverse("Un Texte Avec Des Majuscules"))
    println(minToMinHour(123))
    minToMinHour3?.invoke(123)
}