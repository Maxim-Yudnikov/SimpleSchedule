package com.maxim.simpleschedule.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.maxim.simpleschedule.edit.presentation.EditFragment

interface Screen {
    fun show(fragmentManager: FragmentManager, containerId: Int)
    abstract class Replace(private val fragmentClass: Class<out Fragment>) : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId, fragmentClass.getDeclaredConstructor().newInstance()).commit()
        }
    }

    abstract class ReplaceEdit(private val id: Int) : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId, EditFragment.newInstance(id))
                .addToBackStack("")
                .commit()
        }
    }

    object Pop : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.popBackStack()
        }
    }
}