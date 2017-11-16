package com.example.android.bakingapp.TestSuites;

import com.example.android.bakingapp.DetailsActivityTests.DetailsActivityBasicTabletTests;
import com.example.android.bakingapp.MainActivityTests.MainActivityTabletTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite that contains all tests for tablets
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTabletTests.class,
        DetailsActivityBasicTabletTests.class,
})

public class TabletTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
