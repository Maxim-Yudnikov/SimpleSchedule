package com.maxim.simpleschedule.core.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.maxim.simpleschedule.core.presentation.LessonUi

class LessonConverter {
    @TypeConverter
    fun toJson(list: List<LessonData>) = Gson().toJson(list)

    @TypeConverter
    fun toList(json: String) = Gson().fromJson(json, Array<LessonData>::class.java).toList()
}