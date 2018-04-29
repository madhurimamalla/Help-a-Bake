package mmalla.android.com.helpabake.videoplayer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import timber.log.Timber;

public class VideoPlayerFragment extends Fragment {

    @BindView(R.id.exo_video_player)
    SimpleExoPlayerView mExoPlayerView;
    SimpleExoPlayer mExoPlayer;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private ComponentListener componentListener;

    Recipe recipe;
    RecipeStep recipeStep;
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";

    public VideoPlayerFragment() {
        /**
         * Empty constructor
         */
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_player_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_PARCELABLE);
            recipeStep = savedInstanceState.getParcelable(RECIPE_STEP);
        }
        /**
         * Get the recipe here and play around with it
         */
        else if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE_PARCELABLE);
            recipeStep = getArguments().getParcelable(RECIPE_STEP);
        }
        componentListener = new ComponentListener();

        Timber.d("URL for the recipe step is: " + recipeStep.getVideoURL());
        if(recipeStep.getVideoURL().isEmpty() && !recipeStep.getThumbnailURL().isEmpty()){
            recipeStep.setVideoURL(recipeStep.getThumbnailURL().trim());
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initializePlayer() {

        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());
        mExoPlayer.addListener(componentListener);
        mExoPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(playWhenReady);
        mExoPlayer.seekTo(currentWindow, playbackPosition);

        Uri videoUri = Uri.parse(recipeStep.getVideoURL());
        MediaSource mediaSource = buildMediaSource(videoUri);
        mExoPlayer.prepare(mediaSource, true, false);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.removeListener(componentListener);
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_PARCELABLE, recipe);
        outState.putParcelable(RECIPE_STEP, recipeStep);
    }

    private class ComponentListener extends Player.DefaultEventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Timber.d("changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }
}

