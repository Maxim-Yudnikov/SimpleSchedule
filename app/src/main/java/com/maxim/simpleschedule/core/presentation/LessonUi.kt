package com.maxim.simpleschedule.core.presentation

import android.widget.TextView

abstract class LessonUi {
    open fun same(item: LessonUi): Boolean = false
    open fun show(textView: TextView) {}
    data class Base(private val name: String): LessonUi() {
        override fun same(item: LessonUi) = item is Base && item.name == name
    }
    data class Time(private val time: String): LessonUi() {
        override fun same(item: LessonUi) = item is Time && item.time == time
    }
    object Empty: LessonUi()
}