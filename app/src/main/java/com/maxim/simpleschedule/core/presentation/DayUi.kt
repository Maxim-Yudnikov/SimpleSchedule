package com.maxim.simpleschedule.core.presentation

abstract class DayUi {
    data class Base(
        private val id: Int,
        private val startTime: String,
        private val endTime: String,
        private val lessons: List<LessonUi>
    ) : DayUi()

    object Empty: DayUi()
}