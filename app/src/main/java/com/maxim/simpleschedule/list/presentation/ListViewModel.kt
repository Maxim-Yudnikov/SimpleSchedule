package com.maxim.simpleschedule.list.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.list.domain.ListInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ListViewModel(
    private val interactor: ListInteractor,
    private val communication: ListCommunication.Mutable,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        viewModelScope.launch(dispatcher) {
            communication.update(interactor.getList().map { it.toUi() })
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<DayUi>>) {
        communication.observe(owner, observer)
    }
}