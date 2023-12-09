package com.maxim.simpleschedule.edit.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.maxim.simpleschedule.core.presentation.AbstractCommunication
import com.maxim.simpleschedule.core.presentation.DayUi

interface EditCommunication {
    interface UpdateDay {
        fun updateDay(value: DayUi)
    }
    interface ObserveDay {
        fun observeDay(owner: LifecycleOwner, observer: Observer<DayUi>)
    }
    interface UpdateError: AbstractCommunication.Update<String>
    interface ObserveError: AbstractCommunication.Observe<String>
    interface MutableDay: UpdateDay, ObserveDay
    interface MutableError: UpdateError, ObserveError
    interface All: MutableDay, MutableError {
        fun clear()
    }

    class Base: All, AbstractCommunication.Abstract<String>(MutableLiveData()) {
        private val dayLiveData = MutableLiveData<DayUi>()
        override fun clear() {
            liveData.postValue("")
            dayLiveData.postValue(DayUi.Empty)
        }

        override fun updateDay(value: DayUi) {
            dayLiveData.postValue(value)
        }

        override fun observeDay(owner: LifecycleOwner, observer: Observer<DayUi>) {
            dayLiveData.observe(owner, observer)
        }
    }
}