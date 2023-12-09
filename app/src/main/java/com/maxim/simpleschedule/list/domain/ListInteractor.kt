package com.maxim.simpleschedule.list.domain

import com.maxim.simpleschedule.core.domain.DayDomain

interface ListInteractor {
    suspend fun getList(): List<DayDomain>
}