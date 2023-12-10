package com.maxim.simpleschedule

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.annotation.IdRes
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(
    @IdRes private val recyclerViewId: Int,
) {

    companion object {
        private const val INVALID_ID = -1

        /**
         * Receive a [RecyclerViewMatcher] with the View ID of the [RecyclerView] you want to interact with.
         */
        fun recyclerViewWithId(@IdRes viewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(viewId)
        }
    }

    /**
     * Receive a [Matcher] for the [RecyclerView.ViewHolder.itemView] at a certain position.
     *
     * @param index Zero based index for the row in the [RecyclerView] you want.
     *
     * @return [Matcher] you can run assertions and perform actions on.
     */
    fun itemViewAtIndex(index: Int): Matcher<View> {
        return viewHolderViewAtPosition(index, INVALID_ID)
    }

    /**
     * Receive a [Matcher] for a specific view inside of a [RecyclerView.ViewHolder] at a certain position.
     *
     * @param index Zero based index for the row in the [RecyclerView] you want.
     * @param targetViewId View id in the [RecyclerView.ViewHolder] you want to interact with.
     *
     * @return [Matcher] you can run assertions and perform actions on.
     */
    fun viewHolderViewAtPosition(index: Int, @IdRes targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var itemView: View? = null

            override fun describeTo(description: Description) {
                description.appendText("RecyclerView view not found.")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources

                if (itemView == null) {
                    val recyclerView: RecyclerView? =
                        view.rootView.findViewById(recyclerViewId) as? RecyclerView
                    if (recyclerView != null && recyclerView.id == recyclerViewId) {
                        itemView = recyclerView.findViewHolderForAdapterPosition(index)!!.itemView
                    } else {
                        return false
                    }
                }

                return if (targetViewId == INVALID_ID) {
                    view === itemView
                } else {
                    val targetView = itemView!!.findViewById<View>(targetViewId)
                    view === targetView
                }

            }
        }
    }
}