package com.maxim.simpleschedule.domain

import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.domain.LessonDomain
import com.maxim.simpleschedule.list.data.ListDataSource
import com.maxim.simpleschedule.list.domain.ListInteractor
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ListInteractorTest {

    @Test
    fun test_get_list() = runBlocking {
        val dataSource = FakeListDataSource()
        val interactor = ListInteractor.Base(dataSource)

        dataSource.returnEmpty = true
        var actual = interactor.getList()
        assertEquals(listOf(DayDomain.Base(123,"start", "end", listOf(LessonDomain.Empty))), actual)

        dataSource.returnEmpty = false
        actual = interactor.getList()
        assertEquals(listOf(DayDomain.Base(123,"start", "end", listOf(LessonDomain.Base("name")))), actual)
    }

    private class FakeListDataSource : ListDataSource {
        var returnEmpty = true
        override suspend fun getList(): List<DayDomain> {
            return listOf(
                DayDomain.Base(
                    123,
                    "start",
                    "end",
                    if (returnEmpty) emptyList() else listOf(LessonDomain.Base("name"))
                )
            )
        }
    }
}