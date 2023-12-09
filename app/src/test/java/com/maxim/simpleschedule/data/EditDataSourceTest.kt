package com.maxim.simpleschedule.data

import com.maxim.simpleschedule.core.data.DayRoom
import com.maxim.simpleschedule.core.data.LessonData
import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.domain.EmptyItemNameException
import com.maxim.simpleschedule.core.domain.EmptyLessonListException
import com.maxim.simpleschedule.core.domain.LessonDomain
import com.maxim.simpleschedule.edit.data.DayIdCache
import com.maxim.simpleschedule.edit.data.EditDataSource
import com.maxim.simpleschedule.edit.data.LessonsCache
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EditDataSourceTest {
    private lateinit var dataSource: EditDataSource
    private lateinit var lessonsCache: LessonsCache
    private lateinit var dayIdCache: DayIdCache
    private lateinit var dao: FakeDao
    @Before
    fun before() {
        dao = FakeDao()
        lessonsCache = LessonsCache.Base()
        dayIdCache = DayIdCache.Base()
        dataSource = EditDataSource.Base(dao, dayIdCache, lessonsCache)
    }

    @Test
    fun test_get_day() = runBlocking {
        val actual = dataSource.getDay(5)
        assertEquals(DayDomain.Base(5, "start", "end", emptyList()), actual)
    }

    @Test
    fun test_new_item() {
        dataSource.newItem()
        assertEquals(listOf(LessonData("")), lessonsCache.getList())
    }

    @Test
    fun test_get_cached_day() = runBlocking {
        dataSource.getDay(5)
        var actual = dataSource.getCachedDay()
        assertEquals(DayDomain.Base(5, "\n", "\n", emptyList()), actual)

        dataSource.newItem()
        actual = dataSource.getCachedDay()
        assertEquals(DayDomain.Base(5, "\n", "\n", listOf(LessonDomain.Base(""))), actual)
    }

    @Test
    fun test_rename_item() {
        dataSource.newItem()
        dataSource.newItem()
        dataSource.renameItem(0, "one")
        dataSource.renameItem(1, "two")
        assertEquals(listOf(LessonData("one"), LessonData("two")), lessonsCache.getList())
    }

    @Test
    fun test_delete_item() {
        dataSource.newItem()
        dataSource.newItem()
        dataSource.renameItem(0, "one")
        dataSource.renameItem(1, "two")
        dataSource.deleteItem(0)
        assertEquals(listOf(LessonData("two")), lessonsCache.getList())
    }

    @Test(expected = EmptyLessonListException::class)
    fun test_save_empty_list() = runBlocking {
        dataSource.getDay(1)
        dataSource.save("start", "end")
    }

    @Test
    fun test_save_empty_item_name() = runBlocking {
        dataSource.getDay(1)
        dataSource.newItem()
        dataSource.renameItem(0, "one")
        dataSource.newItem()
        try {
            dataSource.save("start", "end")
        } catch (e: Exception) {
            assertEquals(EmptyItemNameException(1), e)
        }
        assertEquals(listOf(LessonData("one")), lessonsCache.getList())
        assertEquals(1, dayIdCache.getCached())
    }

    @Test
    fun test_save_success() = runBlocking {
        dataSource.getDay(1)
        dataSource.newItem()
        dataSource.renameItem(0, "one")
        dataSource.save("12:00", "19:00")
        assertEquals(listOf(DayRoom(1, "12:00", "19:00", listOf(LessonData("one")))), dao.saveDayList)
        assertEquals(emptyList<LessonData>(), lessonsCache.getList())
        assertEquals(-1, dayIdCache.getCached())
    }

    @Test
    fun test_cancel() = runBlocking {
        dataSource.getDay(3)
        dataSource.newItem()
        dataSource.newItem()
        dataSource.cancel()
        assertEquals(emptyList<LessonData>(), lessonsCache.getList())
        assertEquals(-1, dayIdCache.getCached())
    }
}