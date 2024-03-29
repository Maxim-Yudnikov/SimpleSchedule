package com.maxim.simpleschedule.presentation

import org.junit.Assert.assertEquals

interface Order {
    fun add(value: String)
    fun check(expected: List<String>)

    class Base: Order {
        private val list = mutableListOf<String>()
        override fun add(value: String) {
            list.add(value)
        }

        override fun check(expected: List<String>) {
            assertEquals(expected, list)
        }
    }
}

const val INTERACTOR = "INTERACTOR"
const val NAVIGATION = "NAVIGATION"
const val CLEAR = "CLEAR"
const val CLEAR_COMMUNICATION = "CLEAR_COMMUNICATION"