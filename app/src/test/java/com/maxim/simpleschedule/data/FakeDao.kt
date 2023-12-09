package com.maxim.simpleschedule.data

import com.maxim.simpleschedule.core.data.DayRoom
import com.maxim.simpleschedule.core.data.DaysDao
import com.maxim.simpleschedule.core.data.LessonData

class FakeDao : DaysDao {
    val saveDayList = mutableListOf<DayRoom>()
    var getDayReturnNull = false
    var getDayReturnEmptyList = true
    override suspend fun saveDay(day: DayRoom) {
        saveDayList.add(day)
    }

    override suspend fun getDay(id: Int): DayRoom? {
        return if (getDayReturnNull) null else DayRoom(
            id, "start", "end", if (getDayReturnEmptyList) emptyList()
            else listOf(LessonData("one"), LessonData("two"))
        )
    }

    override suspend fun getList(): List<DayRoom> {
        return listOf(
            DayRoom(0, "start", "end", emptyList()),
            DayRoom(1, "start", "end", emptyList()),
            DayRoom(2, "start", "end", emptyList()),
            DayRoom(3, "start", "end", emptyList()),
            DayRoom(4, "start", "end", emptyList())
        )
    }
}