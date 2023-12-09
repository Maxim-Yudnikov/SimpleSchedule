package com.maxim.simpleschedule.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DayRoom::class], version = 1)
@TypeConverters(LessonConverter::class)
abstract class DaysDatabase: RoomDatabase() {
    abstract fun dao(): DaysDao
}