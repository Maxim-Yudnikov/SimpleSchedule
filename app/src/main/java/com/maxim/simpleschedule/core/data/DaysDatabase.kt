package com.maxim.simpleschedule.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DayRoom::class], version = 1)
abstract class DaysDatabase: RoomDatabase() {
    abstract fun dao(): DaysDao
}