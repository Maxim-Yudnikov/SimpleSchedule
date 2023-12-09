package com.maxim.simpleschedule.core.domain

interface FailureHandler {
    fun handle(e: Exception): Failure

    class Base: FailureHandler {
        override fun handle(e: Exception): Failure {
            return when (e) {
                is EmptyLessonListException -> EmptyLessonListError()
                is EmptyItemNameException -> EmptyItemNameError(e.message!!.toInt())
                else -> UnknownError()
            }
        }
    }
}