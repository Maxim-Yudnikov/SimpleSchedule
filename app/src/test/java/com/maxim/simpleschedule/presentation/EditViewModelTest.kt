package com.maxim.simpleschedule.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.simpleschedule.edit.domain.EditInteractor
import com.maxim.simpleschedule.edit.presentation.EditCommunication
import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.domain.LessonDomain
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.core.presentation.LessonUi
import com.maxim.simpleschedule.core.presentation.Screen
import com.maxim.simpleschedule.edit.presentation.EditViewModel
import com.maxim.simpleschedule.list.domain.SaveResult
import com.maxim.simpleschedule.list.presentation.ListScreen
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EditViewModelTest {
    private lateinit var order: Order
    private lateinit var interactor: FakeEditInteractor
    private lateinit var communication: FakeEditCommunication
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClearViewModel
    private lateinit var viewModel: EditViewModel

    @Before
    fun before() {
        order = Order.Base()
        interactor = FakeEditInteractor(order)
        communication = FakeEditCommunication(order)
        navigation = FakeNavigation(order)
        clear = FakeClearViewModel(order)
        viewModel =
            EditViewModel(interactor, communication, navigation, clear, Dispatchers.Unconfined)
    }

    @Test
    fun test_init() {
        viewModel.init(false, 56)
        assertEquals(emptyList<DayUi>(), communication.dayList)

        viewModel.init(true, 56)
        assertEquals(listOf(DayUi.Base(56, "start", "end", emptyList())), communication.dayList)
    }

    @Test
    fun test_new_item() {
        viewModel.init(true, 56)
        interactor.returnCached = DayDomain.Base(56, "start", "end", listOf(LessonDomain.Base("")))
        viewModel.newItem()
        assertEquals(
            listOf(
                DayUi.Base(56, "start", "end", emptyList()),
                DayUi.Base(56, "start", "end", listOf(LessonUi.Base(""))),
            ),
            communication.dayList
        )
        assertEquals(1, interactor.newItemCounter)
    }

    @Test
    fun test_rename_item() {
        viewModel.init(true, 56)
        interactor.returnCached = DayDomain.Base(56, "start", "end", emptyList())
        viewModel.newItem()
        viewModel.renameItem(0, "name")
        assertEquals(listOf(0), interactor.renameItemIntList)
        assertEquals(listOf("name"), interactor.renameItemStringList)
    }

    @Test
    fun test_delete_item() {
        viewModel.init(true, 56)
        interactor.returnCached = DayDomain.Base(56, "start", "end", listOf(LessonDomain.Base("")))
        viewModel.newItem()
        interactor.returnCached =
            DayDomain.Base(56, "start", "end", listOf(LessonDomain.Base(""), LessonDomain.Base("")))
        viewModel.newItem()
        viewModel.renameItem(0, "one")
        viewModel.renameItem(1, "two")
        interactor.returnCached =
            DayDomain.Base(56, "start", "end", listOf(LessonDomain.Base("two")))
        viewModel.deleteItem(0)
        assertEquals(listOf(0), interactor.deleteItemList)
        assertEquals(
            listOf(
                DayUi.Base(56, "start", "end", emptyList()),
                DayUi.Base(56, "start", "end", listOf(LessonUi.Base(""))),
                DayUi.Base(56, "start", "end", listOf(LessonUi.Base(""), LessonUi.Base(""))),
                DayUi.Base(56, "start", "end", listOf(LessonUi.Base("two"))),
            ), communication.dayList
        )
    }

    @Test
    fun test_save() {
        interactor.saveReturnSuccess = false
        viewModel.save("start", "end")
        assertEquals(listOf("error text"), communication.errorList)
        assertEquals(listOf("start"), interactor.saveItemFirstList)
        assertEquals(listOf("end"), interactor.saveItemSecondList)

        interactor.saveReturnSuccess = true
        viewModel.save("start", "end")
        assertEquals(listOf("error text"), communication.errorList)
        assertEquals(listOf("start", "start"), interactor.saveItemFirstList)
        assertEquals(listOf("end", "end"), interactor.saveItemSecondList)
        assertEquals(listOf(ListScreen), navigation.screens)
        assertEquals(listOf(EditViewModel::class.java), clear.list)
        order.check(listOf(INTERACTOR, INTERACTOR, NAVIGATION, CLEAR_COMMUNICATION, CLEAR))
    }

    @Test
    fun test_cancel() {
        viewModel.cancel()
        assertEquals(listOf(ListScreen), navigation.screens)
        assertEquals(listOf(EditViewModel::class.java), clear.list)
        assertEquals(1, communication.clearCounter)
        order.check(listOf(INTERACTOR, NAVIGATION, CLEAR_COMMUNICATION, CLEAR))
    }


    private class FakeEditInteractor(private val order: Order) : EditInteractor {
        var newItemCounter = 0
        val renameItemIntList = mutableListOf<Int>()
        val renameItemStringList = mutableListOf<String>()
        val deleteItemList = mutableListOf<Int>()
        var saveReturnSuccess = true
        val saveItemFirstList = mutableListOf<String>()
        val saveItemSecondList = mutableListOf<String>()
        var cancelCounter = 0
        var returnCached: DayDomain? = null
        override suspend fun getDay(id: Int): DayDomain {
            return DayDomain.Base(id, "start", "end", emptyList())
        }

        override fun getCachedDay(): DayDomain = returnCached!!

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

        override suspend fun save(startTime: String, endTime: String): SaveResult {
            saveItemFirstList.add(startTime)
            saveItemSecondList.add(endTime)
            order.add(INTERACTOR)
            return if (saveReturnSuccess)
                SaveResult.Success
            else
                SaveResult.Error("error text")
        }

        override fun cancel() {
            cancelCounter++
            order.add(INTERACTOR)
        }
    }

    private class FakeEditCommunication(private val order: Order) : EditCommunication.All {
        val dayList = mutableListOf<DayUi>()
        val errorList = mutableListOf<String>()
        var clearCounter = 0
        override fun clear() {
            clearCounter++
            order.add(CLEAR_COMMUNICATION)
        }

        override fun updateDay(value: DayUi) {
            dayList.add(value)
        }

        override fun observeDay(owner: LifecycleOwner, observer: Observer<DayUi>) {
            throw IllegalStateException("not using in test")
        }

        override fun update(value: String) {
            errorList.add(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<String>) {
            throw IllegalStateException("not using in test")
        }
    }
}