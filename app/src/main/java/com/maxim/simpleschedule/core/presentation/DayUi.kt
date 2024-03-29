package com.maxim.simpleschedule.core.presentation

import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maxim.simpleschedule.R
import com.maxim.simpleschedule.edit.presentation.EditLessonAdapter
import com.maxim.simpleschedule.list.presentation.LessonAdapter
import com.maxim.simpleschedule.list.presentation.ListAdapter

abstract class DayUi {
    open fun same(item: DayUi): Boolean = false
    open fun sameContent(item: DayUi): Boolean = false
    open fun showTitle(textView: TextView) {}
    open fun showTime(startTextView: TextView, endTextView: TextView) {}
    open fun setEnableButtons(addButton: Button, saveButton: Button) {}
    open fun edit(listener: ListAdapter.Listener) {}
    open fun updateLessonAdapter(adapter: LessonAdapter) {}
    open fun updateEditLessonAdapter(adapter: EditLessonAdapter, recyclerView: RecyclerView) {}
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
                0 -> textView.context.getString(R.string.monday)
                1 -> textView.context.getString(R.string.tuesday)
                2 -> textView.context.getString(R.string.wednesday)
                3 -> textView.context.getString(R.string.thursday)
                4 -> textView.context.getString(R.string.friday)
                else -> textView.context.getString(R.string.unknown_day)
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

        override fun updateEditLessonAdapter(
            adapter: EditLessonAdapter,
            recyclerView: RecyclerView
        ) {
            val scroll = adapter.itemCount + 1 == lessons.size
            Log.d("MyLog", "itemCount: ${adapter.itemCount}, lessonsSize: ${lessons.size}")
            adapter.update(lessons, scroll)
            if (scroll)
                recyclerView.scrollToPosition(lessons.size - 1)
        }

        override fun setEnableButtons(addButton: Button, saveButton: Button) {
            addButton.isEnabled = true
            saveButton.isEnabled = true
        }
    }

    object Empty : DayUi() {
        override fun setEnableButtons(addButton: Button, saveButton: Button) {
            addButton.isEnabled = false
            saveButton.isEnabled = false
        }
    }
}