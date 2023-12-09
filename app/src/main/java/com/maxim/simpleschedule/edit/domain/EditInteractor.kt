package com.maxim.simpleschedule.edit.domain

import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.list.domain.SaveResult

interface EditInteractor {
    suspend fun getDay(id: Int): DayDomain
    fun getCachedDay(): DayDomain
    fun newItem()
    fun renameItem(position: Int, newName: String)
    fun deleteItem(position: Int)
    suspend fun save(startTime: String, endTime: String): SaveResult
    fun cancel()
}