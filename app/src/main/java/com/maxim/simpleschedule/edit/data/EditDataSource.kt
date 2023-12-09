package com.maxim.simpleschedule.edit.data

import com.maxim.simpleschedule.core.domain.DayDomain

interface EditDataSource {
    suspend fun getDay(id: Int): DayDomain
    fun getCachedDay(): DayDomain
    fun newItem()
    fun renameItem(position: Int, newName: String)
    fun deleteItem(position: Int)
    suspend fun save(startTime: String, endTime: String)
    fun cancel()
}