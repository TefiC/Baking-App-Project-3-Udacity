package com.example.android.bakingapp.TestSuites;


import com.example.android.bakingapp.MainActivityTests.MainActivityBasicGeneralTests;
import com.example.android.bakingapp.StepsListActivityTests.StepsListGeneralTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite that contains all test that must run correctly
 * both on phones and tablets
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityBasicGeneralTests.class,
        StepsListGeneralTests.class
})

public class GeneralTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}