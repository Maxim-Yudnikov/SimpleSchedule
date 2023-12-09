package com.maxim.simpleschedule.presentation

import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.presentation.ClearViewModel

class FakeClearViewModel(private val order: Order): ClearViewModel {
    val list = mutableListOf<Class<out ViewModel>>()
    override fun clearViewModel(clasz: Class<out ViewModel>) {
        list.add(clasz)
        order.add(CLEAR)
    }
}