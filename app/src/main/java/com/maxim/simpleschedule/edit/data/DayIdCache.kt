package com.maxim.simpleschedule.edit.data

interface DayIdCache {
    fun cache(id: Int)
    fun getCached(): Int
    fun clear()
}