package com.maxim.simpleschedule.core.presentation

abstract class LessonUi {
    data class Base(private val name: String): LessonUi()
    data class Time(private val time: String): LessonUi()
    object Empty: LessonUi()
}