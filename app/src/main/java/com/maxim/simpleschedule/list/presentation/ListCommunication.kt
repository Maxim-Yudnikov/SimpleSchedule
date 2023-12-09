package com.maxim.simpleschedule.list.presentation

import androidx.lifecycle.MutableLiveData
import com.maxim.simpleschedule.core.presentation.AbstractCommunication
import com.maxim.simpleschedule.core.presentation.DayUi

interface ListCommunication {
    interface Update: AbstractCommunication.Update<List<DayUi>>
    interface Observe: AbstractCommunication.Observe<List<DayUi>>
    interface Mutable: Update, Observe
    class Base: Mutable, AbstractCommunication.Abstract<List<DayUi>>(MutableLiveData())
}