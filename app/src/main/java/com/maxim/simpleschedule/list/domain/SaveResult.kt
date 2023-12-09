package com.maxim.simpleschedule.list.domain

abstract class SaveResult {
    object Success: SaveResult()
    data class Error(private val message: String): SaveResult()
}