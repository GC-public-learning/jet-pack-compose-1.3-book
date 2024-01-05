package com.example.myapplication26_flow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class DemoViewModel : ViewModel() {
    // Producer block
    val myFlow: Flow<Int> = flow { // manually emits
        for (i in 0..9) {
            emit(i)
            delay(500)
        }
    }

    // Intermediary
    val newFlow = myFlow.map {
        "Current value = $it"
    }

    // use of filter
    val newFlow2 = myFlow
        .filter { it % 2 == 0 } // keep only even values
        .map {
            "Current value = $it"
        }
    // use of transform (more options than "map")
    val newFlow3 = myFlow
        .transform {
            emit("Value = $it")
            delay(500) // additional delay (doesn't remove or modify the 1st delay)
            val doubled = it * 2
            emit("doubled value = $doubled")
        }

    fun doubleIt(value: Int) = flow {
        emit(value)
        delay(1000)
        emit(value + value)
    }
}