package com.maxim.simpleschedule.core.domain

class EmptyLessonListException: Exception()
class EmptyItemNameException(position: Int): Exception("$position")