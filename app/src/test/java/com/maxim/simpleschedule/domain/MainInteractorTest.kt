package com.maxim.simpleschedule.domain

import com.maxim.simpleschedule.main.data.MainDataSource
import com.maxim.simpleschedule.main.domain.MainInteractor
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MainInteractorTest {

    @Test
    fun test_check() = runBlocking {
        val dataSource = FakeMainDataSource()
        val interactor = MainInteractor.Base(dataSource)
        interactor.checkDays()
        assertEquals(1, dataSource.checkCounter)
    }

    private class FakeMainDataSource: MainDataSource {
        var checkCounter = 0
        override suspend fun checkDays() {
            checkCounter++
        }
    }
}