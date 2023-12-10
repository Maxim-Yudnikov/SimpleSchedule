package com.maxim.simpleschedule

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maxim.simpleschedule.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTests {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_set_day() {
        setDay(listOf("Item 1", "Item 2"), "7:00", "12:00")
        checkLessonsRecyclerView(listOf("7:00", "Item 1", "Item 2", "12:00"))
    }

    @Test
    fun test_delete() {
        test_set_day()
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.editButton)
        ).perform(click())
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.deleteLessonButton)
        ).perform(click())
        onView(withId(R.id.saveButton)).perform(click())
        checkLessonsRecyclerView(listOf("7:00", "Item 2", "12:00"))
    }

    @Test
    fun test_cancel() {
        test_set_day()
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.editButton)
        ).perform(click())
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.deleteLessonButton)
        ).perform(click())
        pressBack()
        onView(withText("Yes")).perform(click())
        checkLessonsRecyclerView(listOf("7:00", "Item 1", "Item 2", "12:00"))
    }

    @Test
    fun test_empty_list_and_empty_lesson_name() {
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.editButton)
        ).perform(click())
        onView(withId(R.id.saveButton)).perform(click())
        onView(withId(R.id.addItemButton)).perform(click())
        onView(withId(R.id.saveButton)).perform(click())
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()))
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.lessonNameEditText)
        ).perform(typeText("Item 1"))
        onView(withId(R.id.saveButton)).perform(click())
        checkLessonsRecyclerView(listOf("Item 1"))
    }


    private fun setDay(items: List<String>, startTime: String, endTime: String) {
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                .viewHolderViewAtPosition(0, R.id.editButton)
        ).perform(click())
        onView(
            RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView).itemViewAtIndex(0)
        ).check(matches(withText("You haven't created any lessons yet")))
        items.forEachIndexed { i, text ->
            onView(withId(R.id.addItemButton)).perform(click())
            onView(
                RecyclerViewMatcher.recyclerViewWithId(R.id.recyclerView)
                    .viewHolderViewAtPosition(i, R.id.lessonNameEditText)
            ).perform(typeText(text))
        }
        onView(withId(R.id.startTimeEditText)).perform(typeText(startTime))
        onView(withId(R.id.endTimeEditText)).perform(typeText(endTime))
        onView(withId(R.id.saveButton)).perform(click())
    }

    private fun checkLessonsRecyclerView(list: List<String>) {
        list.forEachIndexed { i, text ->
            onView(RecyclerViewMatcher.recyclerViewWithId(R.id.lessonsRecyclerView).itemViewAtIndex(i)).check(
                matches(withText(text)))
        }
    }
}