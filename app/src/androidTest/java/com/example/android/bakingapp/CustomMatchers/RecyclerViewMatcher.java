package com.example.android.bakingapp.CustomMatchers;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Based on Danny Roa's RecyclerView Matcher
 * https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
 */

public class RecyclerViewMatcher {

    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            @Override
            public void describeTo(Description description) {

                String recyclerViewIdDescription = Integer.toString(recyclerViewId);
                description.appendText(recyclerViewIdDescription);
            }

            @Override
            protected boolean matchesSafely(View item) {

                this.resources = item.getResources();

                if(childView == null) {
                    RecyclerView recyclerView = item.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                    } else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return item == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return item == targetView;
                }
            }
        };
    }
}
