package com.maxim.simpleschedule.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalStateException

class ListInteractorTest {
    @Test
    fun test_init() {
        val communication = FakeListCommunication()
        val interactor = FakeListInteractor()
        val viewModel = ListViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.init()
        assertEquals(
            listOf(
                DayDomain(
                    id = 0, startTime = "", endTime = "",
                    lessons = emptyList<LessonDomain>()
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
                DayDomain(
                    id = 0,
                    startTime = "",
                    endTime = "",
                    lessons = emptyList<LessonDomain>()
                )
            )
        }
    }
}