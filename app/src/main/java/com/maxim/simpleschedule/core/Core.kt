package com.maxim.simpleschedule.core

import android.content.Context
import androidx.room.Room
import com.maxim.simpleschedule.core.data.DaysDatabase

class Core(context: Context) {
    private val database by lazy {
        Room.databaseBuilder(
            context,
            DaysDatabase::class.java,
            "days_database"
        ).build()
    }

    fun database() = database
}