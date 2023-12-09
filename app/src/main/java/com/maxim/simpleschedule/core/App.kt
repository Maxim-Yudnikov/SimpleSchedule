package com.maxim.simpleschedule.core

import android.app.Application
import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.presentation.ProvideViewModel
import com.maxim.simpleschedule.core.presentation.ViewModelFactory

class App: Application(), ProvideViewModel {
    private lateinit var factory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        factory = ViewModelFactory.Base(Core(this))
    }

    override fun <T : ViewModel> viewModel(clasz: Class<out ViewModel>): T {
        return factory.viewModel(clasz)
    }
}