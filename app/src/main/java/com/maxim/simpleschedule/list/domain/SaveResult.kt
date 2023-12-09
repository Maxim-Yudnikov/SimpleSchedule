package com.maxim.simpleschedule.list.domain

import com.maxim.simpleschedule.edit.presentation.EditCommunication

abstract class SaveResult {
    object Success : SaveResult()
    data class Error(private val message: String) : SaveResult() {
        fun show(communication: EditCommunication.UpdateError) = communication.update(message)
    }
}