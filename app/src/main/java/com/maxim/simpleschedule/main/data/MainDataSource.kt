package com.maxim.simpleschedule.main.data

interface MainDataSource {
    suspend fun checkDays()
}