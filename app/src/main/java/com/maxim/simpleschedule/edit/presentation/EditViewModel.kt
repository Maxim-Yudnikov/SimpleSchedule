package com.maxim.simpleschedule.edit.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.presentation.ClearViewModel
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.core.presentation.LessonUi
import com.maxim.simpleschedule.core.presentation.Navigation
import com.maxim.simpleschedule.core.presentation.Screen
import com.maxim.simpleschedule.edit.domain.EditInteractor
import com.maxim.simpleschedule.list.domain.SaveResult
import com.maxim.simpleschedule.list.presentation.ListScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class EditViewModel(
    private val interactor: EditInteractor,
    private val communication: EditCommunication.All,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    fun init(isFirstRun: Boolean, id: Int) {
        if(isFirstRun) {
            viewModelScope.launch(dispatcher) {
                communication.updateDay(interactor.getDay(id).toUi())
            }
        }
    }

    fun newItem() {
        interactor.newItem()
        communication.updateDay(interactor.getCachedDay().toUi())
    }

    fun renameItem(position: Int, newName: String) {
        interactor.renameItem(position, newName)
    }

    fun deleteItem(position: Int) {
        interactor.deleteItem(position)
        communication.updateDay(interactor.getCachedDay().toUi())
    }

    fun save(startTime: String, endTime: String) {
        viewModelScope.launch(dispatcher) {
            val result = interactor.save(startTime, endTime)
            if (result == SaveResult.Success) {
                navigation.update(ListScreen)
                communication.clear()
                clear.clearViewModel(EditViewModel::class.java)
            } else {
                (result as SaveResult.Error).show(communication)
            }
        }
    }

    fun cancel() {
        interactor.cancel()
        navigation.update(ListScreen)
        communication.clear()
        clear.clearViewModel(EditViewModel::class.java)
    }

    fun observeDay(owner: LifecycleOwner, observer: Observer<DayUi>) {
        communication.observeDay(owner, observer)
    }

    fun observeError(owner: LifecycleOwner, observer: Observer<String>) {
        communication.observe(owner, observer)
    }
}