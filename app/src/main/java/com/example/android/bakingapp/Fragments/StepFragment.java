package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Step;
import com.example.android.bakingapp.Utils.MediaPlayerUtils;
import com.example.android.bakingapp.Utils.RecipeDataUtils;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.android.bakingapp.Utils.MediaPlayerUtils.mMediaSession;

/**
 * Fragment to display a recipe step
 */

public class StepFragment extends Fragment implements Player.EventListener {

    /*
     * View
     */

    @BindView(R.id.step_exoplayer_view) SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.step_text_view) TextView mStepTextView;
    @BindView(R.id.step_main_layout) LinearLayout mStepMainLayout;
    @BindView(R.id.default_step_image) ImageView mStepDefaultImageView;

    /*
     * Constants
     */

    private static final String RECIPE_STEP_KEY = "recipe_step";

    /*
     * Fields
     */

    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private FrameLayout mFullScreenButton;

    private Unbinder unbinder;

    /*
     * Methods
     */

    public static StepFragment newInstance(Step recipeStep) {

        StepFragment stepFragment = new StepFragment();

        // Adds arguments
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_STEP_KEY, recipeStep);
        stepFragment.setArguments(args);

        return stepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if(getArguments() != null && getArguments().containsKey(RECIPE_STEP_KEY)) {
            mStep = getArguments().getParcelable(RECIPE_STEP_KEY);
        }

        mStepTextView.setMovementMethod(new ScrollingMovementMethod());

        if(!mStep.getStepVideoUrl().equals("")) {
            mSimpleExoPlayerView.setVisibility(View.VISIBLE);
            MediaPlayerUtils.initializeExoPlayer(getActivity(), mStep.getStepVideoUrl(), mSimpleExoPlayerView, mExoPlayer);
        } else {
            mStepDefaultImageView.setVisibility(View.VISIBLE);
        }

        mStepTextView.setText(RecipeDataUtils.formatStepDescription(mStep.getStepDescription()));

        // Initialize media player
        MediaPlayerUtils.initFullscreenDialog(getActivity(), mSimpleExoPlayerView, mStepMainLayout);
        MediaPlayerUtils.initFullscreenButton(getActivity(), mSimpleExoPlayerView, mStepMainLayout);

        // Initialize the Media Session.
        MediaPlayerUtils.initializeMediaSession(getActivity(), getActivity().getClass().getSimpleName(), mExoPlayer);

        return rootView;
    }

    public void setStepSelected(Step step) {
        mStep = step;
    }

    /*
     * Lifecycle methods
     */

    @Override
    public void onStop() {
        super.onStop();
        MediaPlayerUtils.releaseExoPlayer(mExoPlayer);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null) {
            mExoPlayer.stop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /*
     * ExoPlayer Listeners
     */

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == Player.STATE_READY) && playWhenReady){
            MediaPlayerUtils.mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == Player.STATE_READY)){
            MediaPlayerUtils.mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(MediaPlayerUtils.mStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
