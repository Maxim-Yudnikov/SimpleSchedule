package com.maxim.simpleschedule.core.presentation

import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.Core

interface ViewModelFactory : ClearViewModel, ProvideViewModel {
    class Base(core: Core) : ViewModelFactory {
        private val provider = ProvideViewModel.Base(core, this)
        private val store = mutableMapOf<Class<out ViewModel>, ViewModel>()
        override fun clearViewModel(clasz: Class<out ViewModel>) {
            store.remove(clasz)
        }

        override fun <T : ViewModel> viewModel(clasz: Class<out ViewModel>): T {
            if (store[clasz] == null)
                store[clasz] = provider.viewModel(clasz)
            return store[clasz] as T
        }
    }
}