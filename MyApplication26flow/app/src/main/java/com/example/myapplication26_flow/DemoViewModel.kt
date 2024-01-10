package com.example.myapplication26_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

// COLD FLOW
// ---------------
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
// HOT FLOW
// -------------

// State flow
class DemoViewMOdel2: ViewModel() {
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun increaseValue() {
        _stateFlow.value +=1
    }
}

// Shared flow
class DemoViewModel3: ViewModel() {
    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 10, // size of the buffer (a new consumer will start by getting the last 10 values already emitted)
        onBufferOverflow = BufferOverflow.DROP_OLDEST // deletion of elements acts like FIFO queue
    )
    val sharedFlow = _sharedFlow.asSharedFlow()
    val subCount = _sharedFlow.subscriptionCount // total of subscribers

    fun startSharedFlow() {
        viewModelScope.launch {
            for(i in 1..5) {
                _sharedFlow.emit(i)
                delay(2000)
            }
        }
    }
}
