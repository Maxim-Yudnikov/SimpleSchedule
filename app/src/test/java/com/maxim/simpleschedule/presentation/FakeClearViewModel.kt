package com.maxim.simpleschedule.presentation

import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.CLEAR
import com.maxim.simpleschedule.Order

class FakeClearViewModel(private val order: Order): ClearViewModel {
    val list = mutableListOf<Class<out ViewModel>>()
    override fun clear(clasz: Class<out ViewModel>) {
        list.add(clasz)
        order.add(CLEAR)
    }
}