package com.maxim.simpleschedule.presentation

import com.maxim.simpleschedule.core.presentation.Screen
import com.maxim.simpleschedule.list.presentation.ListScreen
import com.maxim.simpleschedule.main.domain.MainInteractor
import com.maxim.simpleschedule.main.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test_init() {
        val order = Order.Base()
        val interactor = FakeMainInteractor(order)
        val navigation = FakeNavigation(order)
        val viewModel = MainViewModel(interactor, navigation, Dispatchers.Unconfined)

        viewModel.init(false)
        assertEquals(0, interactor.checkDaysCounter)
        assertEquals(emptyList<Screen>(), navigation.screens)

        viewModel.init(true)
        assertEquals(1, interactor.checkDaysCounter)
        assertEquals(listOf(ListScreen), navigation.screens)
        order.check(listOf(INTERACTOR, NAVIGATION))
    }


    private class FakeMainInteractor(private val order: Order): MainInteractor {
        var checkDaysCounter = 0
        override suspend fun checkDays() {
            checkDaysCounter++
            order.add(INTERACTOR)
        }
    }
}