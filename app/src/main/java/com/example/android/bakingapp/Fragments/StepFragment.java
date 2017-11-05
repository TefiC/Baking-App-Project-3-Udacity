package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Step;
import com.example.android.bakingapp.Utils.MediaPlayerUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Fragment to display a recipe step
 */

public class StepFragment extends Fragment {

    /*
     * Fields
     */

    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private FrameLayout mFullScreenButton;

    private static final String RECIPE_STEP_KEY = "recipe_step";

    /*
     * Methods
     */

    public static StepFragment newInstance(Step recipeStep) {

        StepFragment stepFragment = new StepFragment();

        Bundle args = new Bundle();
        args.putParcelable(RECIPE_STEP_KEY, recipeStep);

        stepFragment.setArguments(args);

        return stepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        mStep = getArguments().getParcelable(RECIPE_STEP_KEY);

        SimpleExoPlayerView simpleExoPlayerView = rootView.findViewById(R.id.step_exoplayer_view);


        if(!mStep.getStepVideoUrl().equals("")) {
            MediaPlayerUtils.initializeExoPlayer(getActivity(), mStep.getStepVideoUrl(), simpleExoPlayerView, mExoPlayer);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }


        TextView textView = rootView.findViewById(R.id.step_text_view);
        textView.setText(mStep.getStepDescription());

        MediaPlayerUtils.initFullscreenDialog(getActivity(), simpleExoPlayerView, (LinearLayout) rootView);
        MediaPlayerUtils.initFullscreenButton(getActivity(), simpleExoPlayerView, (LinearLayout) rootView);

        return rootView;
    }

    public void setStepSelected(Step step) {
        mStep = step;
    }

    @Override
    public void onStop() {
        super.onStop();
        MediaPlayerUtils.releaseExoPlayer(mExoPlayer);
    }
}
