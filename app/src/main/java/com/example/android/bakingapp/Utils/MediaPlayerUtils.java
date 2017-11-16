package com.example.android.bakingapp.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.example.android.bakingapp.Activities.MainActivity.mTabletLayout;

/**
 * Helper methods to setup the recipe videos
 */

public class MediaPlayerUtils {

    /*
     * Constants
     */

    private static final String USER_AGENT_STRING = "BakingApp";


    /*
     * Fields
     */


    private static boolean mIsExoPlayerFullScreen;
    private static Dialog mFullScreenDialog;
    public static MediaSessionCompat mMediaSession;
    public static PlaybackStateCompat.Builder mStateBuilder;


    /*
     * Methods
     */


    /**
     * Initializes ExoPlayer on a SimpleExoPlayerView
     *
     * @param context        The context
     * @param videoUrlString The URL to fetch the video that will be played by ExoPlayer
     * @param playerView     The view where the video will be displayed
     * @param exoPlayer      An ExoPlayer instance
     */
    public static void initializeExoPlayer(Context context, String videoUrlString,
                                           SimpleExoPlayerView playerView, SimpleExoPlayer exoPlayer) {

        if (exoPlayer == null && !videoUrlString.equals("")) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context,
                    trackSelector);

            playerView.setPlayer(exoPlayer);
            MediaSource mediaSource = setupMediaSource(context, videoUrlString);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Stops and releases an instance of SimpleExoPlayer
     *
     * @param simpleExoPlayer An instance of SimpleExoPlayer
     */
    public static void releaseExoPlayer(SimpleExoPlayer simpleExoPlayer) {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    /**
     * Sets up the media source that will be displayed by ExoPlayer
     *
     * @param context        The Context
     * @param videoUrlString The URL of the video that will be displayed
     * @return A MediaSource from an HTTP resource
     */
    private static MediaSource setupMediaSource(Context context, String videoUrlString) {
        // Setup Media Source
        String userAgent = Util.getUserAgent(context, USER_AGENT_STRING);
        return new ExtractorMediaSource(Uri.parse(videoUrlString),
                new DefaultDataSourceFactory(context, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);
    }


    /*
     * Full Screen
     */


    /**
     * Initializes a full screen dialog
     *
     * @param context       The Context
     * @param exoPlayerView A SimpleExoPlayerView
     * @param rootView      The RootView where the dialog will be attached
     */
    public static void initFullscreenDialog(final Context context, final SimpleExoPlayerView exoPlayerView,
                                            final LinearLayout rootView, final SimpleExoPlayer exoPlayer) {

        PlaybackControlView controlView = exoPlayerView.findViewById(R.id.exo_controller);
        final ImageView fullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);

        mFullScreenDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mIsExoPlayerFullScreen) {
                    closeFullscreenDialog(context, exoPlayerView, rootView, fullScreenIcon, exoPlayer);
                    releaseExoPlayer(exoPlayer);
                }
                super.onBackPressed();
            }
        };
    }

    /**
     * Initializes full screen button in ExoPlayer's control panel
     *
     * @param context             The Context
     * @param simpleExoPlayerView A view for the ExoPlayer
     * @param rootView            The layout's root view
     */
    public static void initFullscreenButton(final Context context, final SimpleExoPlayerView simpleExoPlayerView,
                                            final LinearLayout rootView, final SimpleExoPlayer exoPlayer) {

        PlaybackControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        final ImageView fullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        FrameLayout fullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsExoPlayerFullScreen) {
                    openFullscreenDialog(context, simpleExoPlayerView, fullScreenIcon);
                } else {
                    closeFullscreenDialog(context, simpleExoPlayerView, rootView, fullScreenIcon, exoPlayer);
                }
            }
        });
    }

    /**
     * Opens a full screen dialog to display a video in full screen
     *
     * @param context        The Context
     * @param exoPlayerView  The ExoPlayer view
     * @param fullScreenIcon The full screen icon view
     */
    private static void openFullscreenDialog(Context context, SimpleExoPlayerView exoPlayerView, ImageView fullScreenIcon) {

        ViewGroup parentView = (ViewGroup) exoPlayerView.getParent();
        parentView.removeView(exoPlayerView);

        // Update view
        mFullScreenDialog.addContentView(exoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_skrink));

        // Update data
        mIsExoPlayerFullScreen = true;
        exoPlayerView.findViewById(R.id.exo_fullscreen_icon).setTag(R.drawable.ic_fullscreen_skrink);

        // Show the dialog
        mFullScreenDialog.show();
    }

    /**
     * Closes a full screen dialog, placing the video in its original container with
     * its original dimensions
     *
     * @param context        The Context
     * @param exoPlayerView  ExoPlayer's view
     * @param rootView       The layout's root view
     * @param fullScreenIcon ExoPlayer's full screen icon view
     */
    private static void closeFullscreenDialog(Context context, SimpleExoPlayerView exoPlayerView,
                                              LinearLayout rootView, ImageView fullScreenIcon, SimpleExoPlayer exoPlayer) {

        ViewGroup parentView = (ViewGroup) exoPlayerView.getParent();
        LinearLayout stepMainLayout = rootView.findViewById(R.id.step_main_layout);

        parentView.removeView(exoPlayerView);
        stepMainLayout.addView(exoPlayerView, 0);

        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        // If the phone is in portrait orientation
        if (isPortrait) {
            if (mTabletLayout) {
                setExoPlayerLayoutParams(context,
                        isPortrait,
                        mTabletLayout,
                        exoPlayerView,
                        context.getResources().getInteger(R.integer.exo_player_tablet_height));
            } else {
                setExoPlayerLayoutParams(context,
                        isPortrait,
                        mTabletLayout,
                        exoPlayerView,
                        context.getResources().getInteger(R.integer.exo_player_phone_height));
            }

        // If the phone is in landscape orientation
        } else {
            if (mTabletLayout) {
                setExoPlayerLayoutParams(context,
                        isPortrait,
                        mTabletLayout,
                        exoPlayerView,
                        context.getResources().getInteger(R.integer.exo_player_tablet_height));
            } else {
                setExoPlayerLayoutParams(context,
                        isPortrait,
                        mTabletLayout,
                        exoPlayerView,
                        context.getResources().getInteger(R.integer.exo_player_phone_land_width));
            }
        }

        mIsExoPlayerFullScreen = false;
        exoPlayerView.findViewById(R.id.exo_fullscreen_icon).setTag(R.drawable.ic_fullscreen_expand);

        // Change full screen icon
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_expand));

        // Release player and close dialog
        releaseExoPlayer(exoPlayer);
        mFullScreenDialog.dismiss();
    }

    /**
     * Sets the layout parameters for an ExoPlayerSimpleView
     *
     * @param context       The context
     * @param exoPlayerView The ExoPlayerSimpleView
     * @param dimension     The dimension in dp to be set
     */
    private static void setExoPlayerLayoutParams(Context context, boolean isPortrait, boolean isTablet, SimpleExoPlayerView exoPlayerView, int dimension) {
        if(!isTablet) {
            if (isPortrait) {
                exoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        RecipeDataUtils.convertDpToPixels(dimension, context)));
            } else {
                exoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(RecipeDataUtils.convertDpToPixels(dimension, context),
                        ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
            }
        } else {
            exoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    RecipeDataUtils.convertDpToPixels(dimension, context)));
        }

    }
}