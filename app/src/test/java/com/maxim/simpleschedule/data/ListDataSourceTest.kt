package com.maxim.simpleschedule.data

import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.list.data.ListDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ListDataSourceTest {
    @Test
    fun test_get_list() = runBlocking {
        val dao = FakeDao()
        val dataSource = ListDataSource.Base(dao)

        val actual = dataSource.getList()
        assertEquals(listOf(
            DayDomain.Base(0, "start", "end", emptyList()),
            DayDomain.Base(1, "start", "end", emptyList()),
            DayDomain.Base(2, "start", "end", emptyList()),
            DayDomain.Base(3, "start", "end", emptyList()),
            DayDomain.Base(4, "start", "end", emptyList()),
        ), actual)
    }
}