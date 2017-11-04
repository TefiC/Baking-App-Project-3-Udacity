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

    private static void openFullscreenDialog(Context context, SimpleExoPlayerView exoPlayerView, ImageView fullScreenIcon) {

        ((ViewGroup) exoPlayerView.getParent()).removeView(exoPlayerView);
        mFullScreenDialog.addContentView(exoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_skrink));
        mIsExoPlayerFullScreen = true;
        mFullScreenDialog.show();
    }

    private static void closeFullscreenDialog(Context context, SimpleExoPlayerView exoPlayerView,
                                              LinearLayout rootView, ImageView fullScreenIcon) {

        ((ViewGroup) exoPlayerView.getParent()).removeView(exoPlayerView);
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
