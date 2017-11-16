package com.example.android.bakingapp.TestSuites;

import com.example.android.bakingapp.DetailsActivityTests.DetailsActivityBasicPhoneTests;
import com.example.android.bakingapp.MainActivityTests.MainActivityPhoneTests;
import com.example.android.bakingapp.StepsListActivityTests.StepsListBasicPhoneTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite that contains all tests for phones
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityPhoneTests.class,
        DetailsActivityBasicPhoneTests.class,
        StepsListBasicPhoneTests.class
})

public class PhoneTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
