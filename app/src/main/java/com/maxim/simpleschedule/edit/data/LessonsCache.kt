package com.maxim.simpleschedule.edit.data

import com.maxim.simpleschedule.core.data.LessonData

interface LessonsCache {
    fun getList(): List<LessonData>
    fun update(newList: List<LessonData>)
    fun newItem()
    fun renameItem(position: Int, newName: String)
    fun deleteItem(position: Int)
    fun clear()

    class Base: LessonsCache {
        private val list = mutableListOf<LessonData>()
        override fun getList(): List<LessonData> = list

        override fun update(newList: List<LessonData>) {
            list.clear()
            list.addAll(newList)
        }

        override fun newItem() {
            list.add(LessonData(""))
        }

        override fun renameItem(position: Int, newName: String) {
            list[position] = LessonData(newName)
        }

        override fun deleteItem(position: Int) {
            list.removeAt(position)
        }

        override fun clear() {
            list.clear()
        }
    }
}