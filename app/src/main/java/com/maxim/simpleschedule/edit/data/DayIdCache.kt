package com.maxim.simpleschedule.edit.data

interface DayIdCache {
    fun cache(id: Int)
    fun getCached(): Int
    fun clear()

    class Base: DayIdCache {
        private var value: Int = -1
        override fun cache(id: Int) {
            value = id
        }

        override fun getCached(): Int = value

        override fun clear() {
            value = -1
        }
    }
}