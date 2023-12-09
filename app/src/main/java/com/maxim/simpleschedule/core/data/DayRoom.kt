package com.maxim.simpleschedule.core.data

import androidx.room.Entity
import com.maxim.simpleschedule.core.domain.DayDomain

@Entity(tableName = "days_table")
data class DayRoom(
    private val id: Int,
    private val startTime: String,
    private val endTime: String,
    private val lessons: List<LessonData>
) {
    fun toDomain() = DayDomain.Base(id, startTime, endTime, lessons.map { it.toDomain() })
}