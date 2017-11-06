package com.example.android.bakingapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /*
     * Fields
     */

    public static boolean mTabletLayout;
    public static Context mContext;

    @Nullable @BindView (R.id.divider) View mDivider;

    /*
     * Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mContext = this;

        getSupportActionBar().setElevation(0);

        isTabletLayout();
    }

    /**
     * Determines if the layout should be for phone or tablet
     */
    private boolean isTabletLayout() {
        if(mDivider != null) {
            mTabletLayout = true;
            return true;
        } else {
            mTabletLayout = false;
            return false;
        }
    }
}
