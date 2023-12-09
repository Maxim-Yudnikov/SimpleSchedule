package com.maxim.simpleschedule.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxim.simpleschedule.core.domain.DayDomain

@Entity(tableName = "days_table")
data class DayRoom(
    @PrimaryKey val id: Int,
    val startTime: String,
    val endTime: String,
    val lessons: List<LessonData>
) {
    fun toDomain() = DayDomain.Base(id, startTime, endTime, lessons.map { it.toDomain() })
}