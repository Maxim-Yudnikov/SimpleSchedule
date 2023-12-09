package com.maxim.simpleschedule.core.domain

interface Failure {
    fun getMessage(): String
}

class EmptyLessonListError() : Failure {
    override fun getMessage() = "Empty list"
}

class EmptyItemNameError(private val position: Int) : Failure {
    override fun getMessage() = "Empty item name at position ${position + 1}"
}

class UnknownError : Failure {
    override fun getMessage() = "Unknown error"
}