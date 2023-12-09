package com.maxim.simpleschedule.list.domain

import com.maxim.simpleschedule.core.domain.DayDomain
import com.maxim.simpleschedule.list.data.ListDataSource

interface ListInteractor {
    suspend fun getList(): List<DayDomain>

    class Base(private val dataSource: ListDataSource): ListInteractor {
        override suspend fun getList(): List<DayDomain> {
            return dataSource.getList().map { it.checkEmptyLessons() }
        }
    }
}