package com.example.android.bakingapp.Utils;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
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
     * Fields
     */

    private static boolean mIsExoPlayerFullScreen;
    private static Dialog mFullScreenDialog;

    public ImageView mFullScreenIcon;

    /*
     * Methods
     */

    /**
     * Initializes Exoplayer on a SimpleExoPlayerView
     *
     * @param context The context
     * @param videoUrlString The URL to fetch the video that will be played by ExoPlayer
     * @param playerView The view where the video will be displayed
     * @param exoPlayer An ExoPlayer instance
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
        if(simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    /**
     * Sets up the media source that will be displayed by ExoPlayer
     *
     * @param context The Context
     * @param videoUrlString The URL of the video that will be displayed
     *
     * @return A MediaSource from an HTTP resource
     */
    private static MediaSource setupMediaSource(Context context, String videoUrlString) {
        // Setup Media Source
        String userAgent = Util.getUserAgent(context, "BakingApp");
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
     * @param context The Context
     * @param exoPlayerView A SimpleExoPlayerView
     * @param rootView The RootView where the dialog will be attached
     */
    public static void initFullscreenDialog(final Context context, final SimpleExoPlayerView exoPlayerView,
                                             final LinearLayout rootView) {

        PlaybackControlView controlView = exoPlayerView.findViewById(R.id.exo_controller);
        final ImageView fullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);

        mFullScreenDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mIsExoPlayerFullScreen) {
                    closeFullscreenDialog(context, exoPlayerView, rootView, fullScreenIcon);
                }
                super.onBackPressed();
            }
        };
    }

    /**
     * Initializes full screen button in ExoPlayer's control panel
     *
     * @param context The Context
     * @param simpleExoPlayerView A view for the ExoPlayer
     * @param rootView The layout's root view
     */
    public static void initFullscreenButton(final Context context, final SimpleExoPlayerView simpleExoPlayerView,
                                            final LinearLayout rootView) {

        PlaybackControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        final ImageView fullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        FrameLayout fullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsExoPlayerFullScreen) {
                    openFullscreenDialog(context, simpleExoPlayerView, fullScreenIcon);
                } else {
                    closeFullscreenDialog(context, simpleExoPlayerView, rootView, fullScreenIcon);
                }
            }
        });
    }

    /**
     * Opens a full screen dialog to display a video in full screen
     *
     * @param context The Context
     * @param exoPlayerView The ExoPlayer view
     * @param fullScreenIcon The full screen icon view
     */
    private static void openFullscreenDialog(Context context, SimpleExoPlayerView exoPlayerView, ImageView fullScreenIcon) {

        ((ViewGroup) exoPlayerView.getParent()).removeView(exoPlayerView);
        mFullScreenDialog.addContentView(exoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_skrink));
        mIsExoPlayerFullScreen = true;
        mFullScreenDialog.show();
    }

    /**
     * Closes a full screen dialog, placing the video in its original container with
     * its original dimensions
     *
     * @param context The Context
     * @param exoPlayerView ExoPlayer's view
     * @param rootView The layout's root view
     * @param fullScreenIcon ExoPlayer's full screen icon view
     */
    private static void closeFullscreenDialog(Context context, SimpleExoPlayerView exoPlayerView,
                                              LinearLayout rootView, ImageView fullScreenIcon) {

        ((ViewGroup) exoPlayerView.getParent().getParent()).removeView(exoPlayerView);
        ((LinearLayout) rootView.findViewById(R.id.step_main_layout)).addView(exoPlayerView, 0);

        if(mTabletLayout) {
            exoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    RecipeDataUtils.convertDpToPixels(context.getResources().getInteger(R.integer.exo_player_tablet_height), context)));
        } else {
            exoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    RecipeDataUtils.convertDpToPixels(context.getResources().getInteger(R.integer.exo_player_phone_height), context)));
        }

        mIsExoPlayerFullScreen = false;
        mFullScreenDialog.dismiss();
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_expand));
    }
}
