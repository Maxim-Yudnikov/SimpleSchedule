package com.maxim.simpleschedule.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.simpleschedule.CLEAR
import com.maxim.simpleschedule.INTERACTOR
import com.maxim.simpleschedule.NAVIGATION
import com.maxim.simpleschedule.Order
import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.core.presentation.LessonUi
import com.maxim.simpleschedule.core.presentation.Screen
import com.maxim.simpleschedule.list.domain.SaveResult
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
        interactor = FakeEditInteractor()
        communication = FakeEditCommunication()
        navigation = FakeNavigation(order)
        clear = FakeClearViewModel(order)
        viewModel = EditViewModel(interactor, communication, navigation, clear, Dispatchers.Unconfined)
    }

    @Test
    fun test_init() {
        viewModel.init(false, 55)
        assertEquals(emptyList<DayUi>(), communication.dayList)

        viewModel.init(true, 56)
        assertEquals(listOf(DayUi.Base(56, "start", "end", emptyList())), communication.dayList)
    }

    @Test
    fun test_new_item() {
        viewModel.init(true, 56)
        viewModel.newItem()
        assertEquals(
            listOf(DayUi.Base(56, "start", "end", listOf(LessonUi.Base("")))),
            communication.dayList
        )
        assertEquals(1, interactor.newItemCounter)
    }

    @Test
    fun test_rename_item() {
        viewModel.init(true, 56)
        viewModel.newItem()
        viewModel.renameItem(0, "name")
        assertEquals(
            listOf(DayUi.Base(56, "start", "end", listOf(LessonUi.Base("name")))),
            communication.dayList
        )
        assertEquals(listOf(0), interactor.renameItemIntList)
        assertEquals(listOf("name"), interactor.renameItemStringList)
    }

    @Test
    fun test_delete_item() {
        viewModel.init(true, 56)
        viewModel.newItem()
        viewModel.newItem()
        viewModel.renameItem(0, "one")
        viewModel.renameItem(1, "two")
        viewModel.deleteItem(0)
        assertEquals(
            listOf(DayUi.Base(56, "start", "end", listOf(LessonUi.Base("two")))),
            communication.dayList
        )
        assertEquals(listOf(0), interactor.deleteItemList)
    }

    @Test
    fun test_save() {
        interactor.saveReturnSuccess = false
        viewModel.save("start", "end")
        assertEquals(listOf("error text"), communication.errorList)
        assertEquals(listOf("start"), interactor.saveItemFirstList)
        assertEquals(listOf("end"), interactor.saveItemFirstList)

        interactor.saveReturnSuccess = true
        viewModel.save("start", "end")
        assertEquals(listOf("error text"), communication.errorList)
        assertEquals(listOf("start", "start"), interactor.saveItemFirstList)
        assertEquals(listOf("end", "end"), interactor.saveItemFirstList)
        assertEquals(listOf(Screen.Pop), navigation.screens)
        assertEquals(listOf(EditViewModel::class.java), clear)
        order.check(listOf(INTERACTOR, INTERACTOR, NAVIGATION, CLEAR))
    }

    @Test
    fun test_cancel() {
        viewModel.cancel()
        assertEquals(listOf(Screen.Pop), navigation.screens)
        assertEquals(listOf(EditViewModel::class.java), clear)
        order.check(listOf(INTERACTOR, NAVIGATION, CLEAR))
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
        override suspend fun getDay(id: Int): DayDomain {
            return DayDomain.Base(id, "start", "end", emptyList())
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

        override suspend fun save(startTime: String, endTime: String): SaveResult {
            saveItemFirstList.add(startTime)
            saveItemSecondList.add(endTime)
            order.add(INTERACTOR)
            return if(saveReturnSuccess)
                SaveResult.Success
            else
                SaveResult.Error("error text")
        }

        override fun cancel() {
            cancelCounter++
        }
    }

    private class FakeEditCommunication : EditCommunication {
        val dayList = mutableListOf<DayUi>()
        val errorList = mutableListOf<String>()
        var clearCounter = 0
        override fun clear() {
            clearCounter++
        }

        override fun update(value: DayUi) {
            dayList.add(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<DayUi>) {
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