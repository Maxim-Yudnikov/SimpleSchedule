package com.maxim.simpleschedule.core.presentation

import androidx.lifecycle.ViewModel

interface ClearViewModel {
    fun clearViewModel(clasz: Class<out ViewModel>)
}