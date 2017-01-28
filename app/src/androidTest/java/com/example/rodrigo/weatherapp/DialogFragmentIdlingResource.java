package com.example.rodrigo.weatherapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import org.junit.runner.RunWith;

/**
 * DialogFragmentIdlingResource.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@RunWith(AndroidJUnit4.class)
public class DialogFragmentIdlingResource implements IdlingResource {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private FragmentManager mFragmentManager;
    private String mTag;
    private ResourceCallback mResourceCallback;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public DialogFragmentIdlingResource(FragmentManager manager, String tag) {
        mFragmentManager = manager;
        mTag = tag;
    }

    //--------------------------------------------------
    // Idling Resource
    //--------------------------------------------------

    @Override
    public String getName() {
        return DialogFragmentIdlingResource.class.getName() + ":" + mTag;
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = (mFragmentManager.findFragmentByTag(mTag) == null);
        if (idle) {
            mResourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback resourceCallback) {
        mResourceCallback = resourceCallback;
    }
}