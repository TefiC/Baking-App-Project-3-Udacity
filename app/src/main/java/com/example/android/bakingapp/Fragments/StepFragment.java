package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Step;

/**
 * Fragment to display a recipe step
 */

public class StepFragment extends Fragment {

    /*
     * Fields
     */

    private Step mStep;

    /*
     * Methods
     */

    public static StepFragment newInstance() {
        return new StepFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        TextView textView = rootView.findViewById(R.id.step_main_layout);
        textView.setText("STEP FRAGMENT");

        return rootView;
    }
}
