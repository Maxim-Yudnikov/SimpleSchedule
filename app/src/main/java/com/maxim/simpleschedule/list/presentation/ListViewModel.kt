package com.maxim.simpleschedule.list.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.presentation.ClearViewModel
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.core.presentation.Navigation
import com.maxim.simpleschedule.core.presentation.Screen
import com.maxim.simpleschedule.edit.presentation.EditFragment
import com.maxim.simpleschedule.edit.presentation.EditScreen
import com.maxim.simpleschedule.list.domain.ListInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ListViewModel(
    private val interactor: ListInteractor,
    private val communication: ListCommunication.Mutable,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        viewModelScope.launch(dispatcher) {
            communication.update(interactor.getList().map { it.toUi() })
        }
    }
    fun edit(id: Int) {
        navigation.update(EditScreen(id))
        clear.clearViewModel(ListViewModel::class.java)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<DayUi>>) {
        communication.observe(owner, observer)
    }
}