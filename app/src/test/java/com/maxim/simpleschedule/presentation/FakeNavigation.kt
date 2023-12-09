package com.maxim.simpleschedule.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.simpleschedule.core.presentation.Navigation
import com.maxim.simpleschedule.core.presentation.Screen
import java.lang.IllegalStateException

class FakeNavigation(private val order: Order): Navigation.Mutable {
    val screens = mutableListOf<Screen>()
    override fun update(value: Screen) {
        screens.add(value)
        order.add(NAVIGATION)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<Screen>) {
        throw IllegalStateException("not using in test")
    }
}