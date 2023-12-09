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
        dataSource.checkDays()
        assertEquals(listOf(
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
        ), dao.saveDayList)

        assertEquals(listOf(
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
            DayRoom(0, "", "", emptyList()),
        ), dao.saveDayList)
    }
}