package com.maxim.simpleschedule.data

import com.maxim.simpleschedule.core.data.DayRoom
import com.maxim.simpleschedule.core.data.DaysDao

class FakeDao: DaysDao {
    val saveDayList = mutableListOf<DayRoom>()
    override suspend fun saveDay(day: DayRoom) {
        saveDayList.add(day)
    }
}