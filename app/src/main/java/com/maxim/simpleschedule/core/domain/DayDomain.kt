package com.maxim.simpleschedule.core.domain

import com.maxim.simpleschedule.core.presentation.DayUi

abstract class DayDomain {
    abstract fun toUi(): DayUi
    data class Base(
        private val id: Int,
        private val startTime: String,
        private val endTime: String,
        private val lessons: List<LessonDomain>
    ): DayDomain() {
        override fun toUi() = DayUi.Base(id, startTime, endTime, lessons.map { it.toUi() })
    }

    object Empty: DayDomain() {
        override fun toUi() = DayUi.Empty
    }
}
