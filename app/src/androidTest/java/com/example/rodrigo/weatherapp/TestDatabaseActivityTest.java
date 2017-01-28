package com.example.rodrigo.weatherapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rodrigo.weatherapp.view.activity.test.TestDatabaseActivity;
import com.example.rodrigo.weatherapp.view.fragment.LoadingDialogDatabaseFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * TestDatabaseActivityTest.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@RunWith(AndroidJUnit4.class)
public class TestDatabaseActivityTest {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @Rule
    public ActivityTestRule<TestDatabaseActivity> mActivityRule = new ActivityTestRule<>(TestDatabaseActivity.class);

    //--------------------------------------------------
    // Test
    //--------------------------------------------------

    @Test
    public void done() {
        IdlingResource idlingResource = new DialogFragmentIdlingResource(
            mActivityRule.getActivity().getSupportFragmentManager(),
            LoadingDialogDatabaseFragment.TAG);

        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.id_activity_test_database__text_view)).check(matches(withText(R.string.dialog_ok)));
        Espresso.unregisterIdlingResources(idlingResource);
    }
}