package com.aaron.sample.dragndroplist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var users by mutableStateOf((1..100).map { User(it, "Name: $it") })
        private set

    fun swapUser(from: Int, to: Int) {
        users = users.toMutableList().apply {
            add(to, removeAt(from))
        }
    }

}