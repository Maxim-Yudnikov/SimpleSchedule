package com.maxim.simpleschedule.main.domain

import com.maxim.simpleschedule.main.data.MainDataSource

interface MainInteractor {
    suspend fun checkDays()

    class Base(private val dataSource: MainDataSource): MainInteractor {
        override suspend fun checkDays() {
            dataSource.checkDays()
        }
    }
}