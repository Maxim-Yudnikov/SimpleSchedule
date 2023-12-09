package com.maxim.simpleschedule.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface AbstractCommunication {
    interface Update<T : Any> {
        fun update(value: T)
    }

    interface Observe<T : Any> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    abstract class Abstract<T : Any>(
        protected val liveData: MutableLiveData<T>
    ) : Update<T>, Observe<T>
    {
        override fun update(value: T) {
            liveData.value = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }
}