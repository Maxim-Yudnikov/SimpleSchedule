package com.maxim.simpleschedule.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.lang.IllegalStateException

class FakeNavigation: Navigation.Mutable {
    val screens = mutableListOf<Screen>()
    override fun update(value: Screen) {
        screens.add(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<Screen>) {
        throw IllegalStateException("not using in test")
    }
}