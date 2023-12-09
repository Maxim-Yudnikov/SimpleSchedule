package com.maxim.simpleschedule.core.presentation

import android.widget.TextView
import com.maxim.simpleschedule.edit.presentation.EditLessonAdapter
import com.maxim.simpleschedule.list.presentation.LessonAdapter
import com.maxim.simpleschedule.list.presentation.ListAdapter

abstract class DayUi {
    open fun same(item: DayUi): Boolean = false
    open fun sameContent(item: DayUi): Boolean = false
    open fun showTitle(textView: TextView) {}
    open fun showTime(startTextView: TextView, endTextView: TextView) {}
    open fun edit(listener: ListAdapter.Listener) {}
    open fun updateLessonAdapter(adapter: LessonAdapter) {}
    open fun updateEditLessonAdapter(adapter: EditLessonAdapter) {}
    data class Base(
        private val id: Int,
        private val startTime: String,
        private val endTime: String,
        private val lessons: List<LessonUi>
    ) : DayUi() {
        override fun same(item: DayUi) = item is Base && item.id == id
        override fun sameContent(item: DayUi) =
            item is Base && item.endTime == endTime && item.startTime == startTime && item.lessons == lessons

        override fun showTitle(textView: TextView) {
            textView.text = when (id) {
                0 -> "Monday"
                1 -> "Tuesday"
                2 -> "Wednesday"
                3 -> "Thursday"
                4 -> "Friday"
                5 -> "Saturday"
                6 -> "Sunday"
                else -> "Unknown day"
            }
        }

        override fun showTime(startTextView: TextView, endTextView: TextView) {
            if (startTime == "\n" || endTime == "\n")
                return
            startTextView.text = startTime
            endTextView.text = endTime
        }

        override fun edit(listener: ListAdapter.Listener) {
            listener.edit(id)
        }

        override fun updateLessonAdapter(adapter: LessonAdapter) {
            val list = mutableListOf<LessonUi>()
            if (startTime.isNotEmpty())
                list.add(LessonUi.Time(startTime))
            list.addAll(lessons)
            if (endTime.isNotEmpty())
                list.add(LessonUi.Time(endTime))
            adapter.update(list)
        }

        override fun updateEditLessonAdapter(adapter: EditLessonAdapter) {
            adapter.update(lessons)
        }
    }

    object Empty : DayUi()
}