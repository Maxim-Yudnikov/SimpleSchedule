package com.maxim.simpleschedule.core.presentation

import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.core.Core
import com.maxim.simpleschedule.core.domain.FailureHandler
import com.maxim.simpleschedule.edit.data.DayIdCache
import com.maxim.simpleschedule.edit.data.EditDataSource
import com.maxim.simpleschedule.edit.data.LessonsCache
import com.maxim.simpleschedule.edit.domain.EditInteractor
import com.maxim.simpleschedule.edit.presentation.EditCommunication
import com.maxim.simpleschedule.edit.presentation.EditViewModel
import com.maxim.simpleschedule.list.data.ListDataSource
import com.maxim.simpleschedule.list.domain.ListInteractor
import com.maxim.simpleschedule.list.presentation.ListCommunication
import com.maxim.simpleschedule.list.presentation.ListViewModel
import com.maxim.simpleschedule.main.data.MainDataSource
import com.maxim.simpleschedule.main.domain.MainInteractor
import com.maxim.simpleschedule.main.presentation.MainViewModel
import java.lang.IllegalStateException

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clasz: Class<out ViewModel>): T

    class Base(core: Core, private val clear: ClearViewModel) : ProvideViewModel {
        private val navigation = Navigation.Base()
        private val dao = core.database().dao()
        override fun <T : ViewModel> viewModel(clasz: Class<out ViewModel>): T {
            return when (clasz) {
                MainViewModel::class.java ->
                    MainViewModel(MainInteractor.Base(MainDataSource.Base(dao)), navigation)
                ListViewModel::class.java ->
                    ListViewModel(
                        ListInteractor.Base(ListDataSource.Base(dao)),
                        ListCommunication.Base(),
                        navigation
                    )
                EditViewModel::class.java ->
                    EditViewModel(
                        EditInteractor.Base(
                            EditDataSource.Base(
                                dao, DayIdCache.Base(),
                                LessonsCache.Base()
                            ), FailureHandler.Base()
                        ), EditCommunication.Base(), navigation, clear
                    )
                else -> throw IllegalStateException("unknown viewModel $clasz")
            } as T
        }
    }
}