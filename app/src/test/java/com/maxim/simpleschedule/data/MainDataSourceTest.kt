package com.maxim.simpleschedule.data

import com.maxim.simpleschedule.core.data.DayRoom
import com.maxim.simpleschedule.main.data.MainDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MainDataSourceTest {
    @Test
    fun test_check_days() = runBlocking {
        val dao = FakeDao()
        val dataSource = MainDataSource.Base(dao)

        dao.getDayReturnNull = true
        dataSource.checkDays()
        assertEquals(listOf(
            DayRoom(0, "", "", emptyList()),
            DayRoom(1, "", "", emptyList()),
            DayRoom(2, "", "", emptyList()),
            DayRoom(3, "", "", emptyList()),
            DayRoom(4, "", "", emptyList()),
        ), dao.saveDayList)

        dao.getDayReturnNull = false
        dataSource.checkDays()
        assertEquals(listOf(
            DayRoom(0, "", "", emptyList()),
            DayRoom(1, "", "", emptyList()),
            DayRoom(2, "", "", emptyList()),
            DayRoom(3, "", "", emptyList()),
            DayRoom(4, "", "", emptyList()),
        ), dao.saveDayList)
    }
}