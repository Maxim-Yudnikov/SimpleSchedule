package com.maxim.simpleschedule.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDay(day: DayRoom)

    @Query("SELECT * FROM days_table WHERE id IS :id")
    suspend fun getDay(id: Int): DayRoom?

    @Query("SELECT * FROM days_table")
    suspend fun getList(): List<DayRoom>
}