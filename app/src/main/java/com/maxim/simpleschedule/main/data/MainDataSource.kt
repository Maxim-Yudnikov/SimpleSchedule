package com.maxim.simpleschedule.main.data

import com.maxim.simpleschedule.core.data.DayRoom
import com.maxim.simpleschedule.core.data.DaysDao

interface MainDataSource {
    suspend fun checkDays()

    class Base(private val dao: DaysDao): MainDataSource {
        override suspend fun checkDays() {
            for (id in 0 until 5) {
                if (dao.getDay(id) == null)
                    dao.saveDay(DayRoom(id, "", "", emptyList()))
            }
        }
    }
}