package com.maxim.simpleschedule.core.domain

import com.maxim.simpleschedule.core.presentation.LessonUi

abstract class LessonDomain {
    abstract fun toUi(): LessonUi
    data class Base(private val name: String): LessonDomain() {
        override fun toUi() = LessonUi.Base(name)
    }

    data class Time(private val time: String): LessonDomain() {
        override fun toUi() = LessonUi.Time(time)
    }

    object Empty: LessonDomain() {
        override fun toUi() = LessonUi.Empty
    }
}