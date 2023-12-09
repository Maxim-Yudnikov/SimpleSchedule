package com.maxim.simpleschedule.edit.data

import com.maxim.simpleschedule.core.data.DayRoom
import com.maxim.simpleschedule.core.data.DaysDao
import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.domain.EmptyItemNameException
import com.maxim.simpleschedule.core.domain.EmptyLessonListException
import com.maxim.simpleschedule.core.domain.LessonDomain

interface EditDataSource {
    suspend fun getDay(id: Int): DayDomain
    fun getCachedDay(): DayDomain
    fun newItem()
    fun renameItem(position: Int, newName: String)
    fun deleteItem(position: Int)
    suspend fun save(startTime: String, endTime: String)
    fun cancel()

    class Base(
        private val dao: DaysDao,
        private val dayIdCache: DayIdCache,
        private val lessonsCache: LessonsCache
    ) : EditDataSource {
        //todo not tested getDay and put in lessonsCache
        override suspend fun getDay(id: Int): DayDomain {
            dayIdCache.cache(id)
            val day = dao.getDay(id)!!
            lessonsCache.update(day.lessons)
            return day.toDomain()
        }

        override fun getCachedDay(): DayDomain {
            return DayDomain.Base(
                dayIdCache.getCached(), "\n", "\n",
                lessonsCache.getList().map { it.toDomain() })
        }

        override fun newItem() {
            lessonsCache.newItem()
        }

        override fun renameItem(position: Int, newName: String) {
            lessonsCache.renameItem(position, newName)
        }

        override fun deleteItem(position: Int) {
            lessonsCache.deleteItem(position)
        }

        override suspend fun save(startTime: String, endTime: String) {
            val lessons = lessonsCache.getList()
            if (lessons.isEmpty()) throw EmptyLessonListException()
            lessons.forEachIndexed { i, lesson ->
                if(lesson.name.isEmpty())
                    throw EmptyItemNameException(i)
            }

            dao.saveDay(DayRoom(dayIdCache.getCached(), startTime, endTime, ArrayList(lessons)))
            lessonsCache.clear()
            dayIdCache.clear()
        }

        override fun cancel() {
            lessonsCache.clear()
            dayIdCache.clear()
        }
    }
}