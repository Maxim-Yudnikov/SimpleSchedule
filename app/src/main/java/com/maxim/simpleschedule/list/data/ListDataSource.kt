package com.maxim.simpleschedule.list.data

import com.maxim.simpleschedule.core.data.DaysDao
import com.maxim.simpleschedule.core.domain.DayDomain

interface ListDataSource {
    suspend fun getList(): List<DayDomain>

    class Base(private val dao: DaysDao) : ListDataSource {
        override suspend fun getList() = dao.getList().map { it.toDomain() }
    }
}