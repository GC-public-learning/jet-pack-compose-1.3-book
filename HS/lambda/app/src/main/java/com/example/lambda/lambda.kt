package com.example.lambda

import android.util.Log

fun main() {
    println("Hello Android !")
    val modOperation = { a: Int -> a % 2 == 0 } // lambda fun
    println(modOperation(8))

    //---------------------------------------------------
    lineLogger(
        { // --> lambda function with 3 functions inside
            println("message 1")
            println("message 2")
            println("message 3")
        },
        100
    )
    println("""
      line 1
      line 2
      line 3
      """
    )
    println("""
        | line 1 without margin
        line 2 with margin
        | line 3 without margin
        """.trimMargin()
    )
    // --------------------------------------------------

    println(fun1(::convertIntToString, 10)) // :: a named function as param
    println(fun1(::convertIntToStringAndHelloMessage, 20)) // :: a named function as param
    println(fun1( { num: Int -> num.toString() }, 30)) // a lambda as param
    val const1 = { num: Int -> num.toString() }
    println(fun1(const1, 10)) // a const whose a lambada is assigned to as param

    // --------------------------------------------------

    var num = 0
    val const2 = { n: Int, n2: Int -> // params
        var var1 = n + n2 // body
        num = var1 * 10 // body
        num// return (no assigniation, just a value )
    }
    fun2(const2, 100, 200)

    val const3 = { num: Int, num2 : Int -> num + num2 }
    fun2(const3, 10, 20)
    // --------------------------------------------------

    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

}

// lambdaName = block
// inputType = ()
// returnType = Unit (no return)
fun lineLogger(block : () -> Unit, a : Int) { // high order fun
    repeat(5, { println("-----") })
    block()
    repeat(5, { println("-----") })

}
fun convertIntToString (num : Int) : String {
    return num.toString()
}
fun convertIntToStringAndHelloMessage (num : Int) : String {
    return "Hello ! $num"
}
// high order fun
fun fun1 (fun2 : (Int) -> String, num : Int) : String {
    return fun2(num)
}
// high order fun
fun fun2 (fun1 : (Int, Int) -> Int, num1 : Int, num2 : Int) : String{
    return fun1(num1, num2).toString()
}




