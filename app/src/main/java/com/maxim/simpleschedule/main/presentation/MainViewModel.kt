package com.maxim.simpleschedule.main.presentation

import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.presentation.Navigation
import com.maxim.simpleschedule.list.presentation.ListScreen
import com.maxim.simpleschedule.main.domain.MainInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: MainInteractor,
    private val navigation: Navigation.Mutable,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            viewModelScope.launch(dispatcher) {
                interactor.checkDays()
                navigation.update(ListScreen)
            }
        }
    }
}