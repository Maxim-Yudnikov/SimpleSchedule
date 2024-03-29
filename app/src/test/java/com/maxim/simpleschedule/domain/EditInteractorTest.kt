package com.maxim.simpleschedule.domain

import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.domain.EmptyLessonListException
import com.maxim.simpleschedule.core.domain.FailureHandler
import com.maxim.simpleschedule.core.domain.LessonDomain
import com.maxim.simpleschedule.core.presentation.LessonUi
import com.maxim.simpleschedule.edit.data.EditDataSource
import com.maxim.simpleschedule.edit.domain.EditInteractor
import com.maxim.simpleschedule.list.domain.SaveResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EditInteractorTest {
    private lateinit var interactor: EditInteractor
    private lateinit var dataSource: FakeEditDataSource

    @Before
    fun before() {
        dataSource = FakeEditDataSource()
        interactor = EditInteractor.Base(dataSource, FailureHandler.Base())
    }

    @Test
    fun test_get_day() = runBlocking {
        dataSource.getDayReturnEmpty = true
        var actual = interactor.getDay(123)
        assertEquals(DayDomain.Base(123, "start", "end", listOf(LessonDomain.Empty)), actual)

        dataSource.getDayReturnEmpty = false
        actual = interactor.getDay(123)
        assertEquals(DayDomain.Base(123, "start", "end", listOf(LessonDomain.Base("name"))), actual)
    }

    @Test
    fun test_get_cached_day() {
        val actual = interactor.getCachedDay()
        assertEquals(DayDomain.Base(666, "cached", "day", listOf(LessonDomain.Empty)), actual)
    }

    @Test
    fun test_new_item() {
        interactor.newItem()
        assertEquals(1, dataSource.newItemCounter)
    }

    @Test
    fun test_rename_item() {
        interactor.renameItem(45, "new name")
        assertEquals(listOf(45), dataSource.renameItemIntList)
        assertEquals(listOf("new name"), dataSource.renameItemStringList)
    }

    @Test
    fun test_delete_item() {
        interactor.deleteItem(5)
        assertEquals(listOf(5), dataSource.deleteItemList)
    }

    @Test
    fun test_save_success() = runBlocking {
        dataSource.saveReturnSuccess = true
        val actual = interactor.save("start", "end")
        assertEquals(SaveResult.Success, actual)
        assertEquals(listOf("start"), dataSource.saveFirstList)
        assertEquals(listOf("end"), dataSource.saveSecondList)
    }

    @Test
    fun test_save_failure() = runBlocking {
        dataSource.saveReturnSuccess = false
        val actual = interactor.save("start", "end")
        assertEquals(SaveResult.Error("Empty list"), actual)
        assertEquals(listOf("start"), dataSource.saveFirstList)
        assertEquals(listOf("end"), dataSource.saveSecondList)
    }

    @Test
    fun test_cancel() {
        interactor.cancel()
        assertEquals(1, dataSource.cancelCounter)
    }


    private class FakeEditDataSource : EditDataSource {
        var saveReturnSuccess = true
        var newItemCounter = 0
        val renameItemIntList = mutableListOf<Int>()
        val renameItemStringList = mutableListOf<String>()
        val deleteItemList = mutableListOf<Int>()
        val saveFirstList = mutableListOf<String>()
        val saveSecondList = mutableListOf<String>()
        var cancelCounter = 0
        var getDayReturnEmpty = true
        override suspend fun getDay(id: Int): DayDomain {
            return DayDomain.Base(
                id, "start", "end",
                if (getDayReturnEmpty) emptyList() else listOf(LessonDomain.Base("name"))
            )
        }

        override fun getCachedDay(): DayDomain {
            return DayDomain.Base(666, "cached", "day", emptyList())
        }

        override fun newItem() {
            newItemCounter++
        }

        override fun renameItem(position: Int, newName: String) {
            renameItemIntList.add(position)
            renameItemStringList.add(newName)
        }

        override fun deleteItem(position: Int) {
            deleteItemList.add(position)
        }

        override suspend fun save(startTime: String, endTime: String) {
            saveFirstList.add(startTime)
            saveSecondList.add(endTime)
            if (!saveReturnSuccess)
                throw EmptyLessonListException()
        }

        override fun cancel() {
            cancelCounter++
        }
    }
}