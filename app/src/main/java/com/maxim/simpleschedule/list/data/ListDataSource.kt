package com.maxim.simpleschedule.list.data

import com.maxim.simpleschedule.core.domain.DayDomain

interface ListDataSource {
    suspend fun getList(): List<DayDomain>
}