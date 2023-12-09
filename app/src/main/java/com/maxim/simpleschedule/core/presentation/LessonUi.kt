package com.maxim.simpleschedule.core.presentation

import android.widget.TextView

abstract class LessonUi {
    open fun same(item: LessonUi): Boolean = false
    open fun show(textView: TextView) {}
    data class Base(private val name: String): LessonUi() {
        override fun same(item: LessonUi) = item is Base && item.name == name
        override fun show(textView: TextView) {
            textView.text = name
        }
    }
    data class Time(private val time: String): LessonUi() {
        override fun same(item: LessonUi) = item is Time && item.time == time
        override fun show(textView: TextView) {
            textView.text = time
        }
    }
    object Empty: LessonUi() {
        override fun same(item: LessonUi) = item is Empty
    }
}