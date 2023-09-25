package com.example.myapplication1
var y = 1
fun main() {
    println("result : ${String::class.objectInstance}")

    fun isOdd(x: Int) = x % 2 != 0
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))

    println(::y.get())
    println(::y.name)
    println("${::y.set(2)}, $y")
}