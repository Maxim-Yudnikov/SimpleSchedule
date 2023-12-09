package com.maxim.simpleschedule.core.domain

interface Failure {
    fun getMessage(): String
}

class EmptyLessonListError(private val message: String): Failure {
    override fun getMessage() = message
}

class UnknownError: Failure {
    override fun getMessage() = "Unknown error"
}