package com.maxim.simpleschedule.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.list.domain.ListInteractor
import com.maxim.simpleschedule.list.presentation.ListCommunication
import com.maxim.simpleschedule.list.presentation.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class ListViewModelTest {
    @Test
    fun test_init() {
        val communication = FakeListCommunication()
        val interactor = FakeListInteractor()
        val viewModel = ListViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.init()
        assertEquals(
            listOf(
                DayUi.Base(
                    id = 0, startTime = "", endTime = "",
                    lessons = emptyList()
                )
            ), communication.list
        )
    }


    private class FakeListCommunication : ListCommunication.Mutable {
        val list = mutableListOf<DayUi>()
        override fun update(value: List<DayUi>) {
            list.addAll(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<DayUi>>) {
            throw IllegalStateException("not using in test")
        }
    }

    private class FakeListInteractor : ListInteractor {
        override suspend fun getList(): List<DayDomain> {
            return listOf(
                DayDomain.Base(
                    id = 0,
                    startTime = "",
                    endTime = "",
                    lessons = emptyList()
                )
            )
        }
    }
}