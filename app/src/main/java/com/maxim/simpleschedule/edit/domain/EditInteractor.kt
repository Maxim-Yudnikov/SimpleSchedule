package com.maxim.simpleschedule.edit.domain

import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.core.domain.FailureHandler
import com.maxim.simpleschedule.edit.data.EditDataSource
import com.maxim.simpleschedule.list.domain.SaveResult

interface EditInteractor {
    suspend fun getDay(id: Int): DayDomain
    fun getCachedDay(): DayDomain
    fun newItem()
    fun renameItem(position: Int, newName: String)
    fun deleteItem(position: Int)
    suspend fun save(startTime: String, endTime: String): SaveResult
    fun cancel()

    class Base(private val dataSource: EditDataSource, private val failureHandler: FailureHandler): EditInteractor {
        override suspend fun getDay(id: Int): DayDomain {
            return dataSource.getDay(id).checkEmptyLessons()
        }

        override fun getCachedDay(): DayDomain {
            return dataSource.getCachedDay().checkEmptyLessons()
        }

        override fun newItem() {
            dataSource.newItem()
        }

        override fun renameItem(position: Int, newName: String) {
            dataSource.renameItem(position, newName)
        }

        override fun deleteItem(position: Int) {
            dataSource.deleteItem(position)
        }

        override suspend fun save(startTime: String, endTime: String): SaveResult {
            return try {
                dataSource.save(startTime, endTime)
                SaveResult.Success
            } catch (e: Exception) {
                SaveResult.Error(failureHandler.handle(e).getMessage())
            }
        }

        override fun cancel() {
            dataSource.cancel()
        }
    }
}