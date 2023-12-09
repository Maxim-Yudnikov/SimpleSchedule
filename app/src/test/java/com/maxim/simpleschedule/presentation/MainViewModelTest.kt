package com.maxim.simpleschedule.presentation

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test_init() {
        val interactor = FakeMainInteractor()
        val navigation = FakeNavigation()
        val viewModel = MainViewModel(interactor, navigation, Dispatchers.Unconfined)

        viewModel.init(false)
        assertEquals(0, interactor.checkDaysCounter)
        assertEquals(emptyList<Screen>(), navigation.screens)

        viewModel.init(true)
        assertEquals(1, interactor.checkDaysCounter)
        assertEquals(listOf(ListScreen), navigation.screens)
    }


    private class FakeMainInteractor: MainInteractor {
        var checkDaysCounter = 0
        override suspend fun checkDays() {
            checkDaysCounter++
        }
    }
}