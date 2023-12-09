package com.maxim.simpleschedule

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