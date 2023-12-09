package com.maxim.simpleschedule.core.data

import com.maxim.simpleschedule.core.domain.LessonDomain

data class LessonData(private val name: String) {
    fun toDomain() = LessonDomain.Base(name)
}