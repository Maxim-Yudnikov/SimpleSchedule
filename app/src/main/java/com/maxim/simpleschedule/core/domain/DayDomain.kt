package com.maxim.simpleschedule.core.domain

import com.maxim.simpleschedule.core.presentation.DayUi

abstract class DayDomain {
    abstract fun toUi(): DayUi
    abstract fun checkEmptyLessons(): DayDomain
    data class Base(
        private val id: Int,
        private val startTime: String,
        private val endTime: String,
        private val lessons: List<LessonDomain>
    ) : DayDomain() {
        override fun toUi() = DayUi.Base(id, startTime, endTime, lessons.map { it.toUi() })

        override fun checkEmptyLessons() =
            if (lessons.isNotEmpty()) this else this.copy(lessons = listOf(LessonDomain.Empty))
    }
}
