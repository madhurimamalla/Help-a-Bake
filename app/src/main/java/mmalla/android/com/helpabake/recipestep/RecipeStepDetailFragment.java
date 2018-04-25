package mmalla.android.com.helpabake.recipestep;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.videoplayer.VideoPlayerFragment;
import mmalla.android.com.helpabake.recipe.Recipe;
import timber.log.Timber;

public class RecipeStepDetailFragment extends Fragment {

    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";
    public static final String TWO_PANE = "TWO_PANE";
    public Boolean mTwoPane;

    public RecipeStepDetailFragment() {
        /**
         * Mandatory empty constructor for the fragment
         */
    }

    @Nullable
    @BindView(R.id.recipe_step_detail_textview)
    public TextView mRecipeStepDesc;

    @BindView(R.id.video_not_available_textview)
    public TextView mVideoNotAvailable;

    @BindView(R.id.button_to_previous_step)
    public Button mPreviousButton;

    @BindView(R.id.button_to_next_step)
    public Button mNextButton;

    Recipe recipe;
    RecipeStep recipeStep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_step_detail_fragment, container, false);

        /**
         * Bind the views using ButterKnife
         */
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            recipeStep = getArguments().getParcelable(RECIPE_STEP);
            recipe = getArguments().getParcelable(RECIPE_EXTRA_INTENT);
            mTwoPane = getArguments().getBoolean(TWO_PANE);
        }
        Timber.d("Recipe name: " + recipe.getName());
        Timber.d("Recipe step clicked was: " + recipeStep.getShortDescription());
        Toast.makeText(getActivity(), "recipeStep: " + recipeStep.getShortDescription(), Toast.LENGTH_SHORT);

        if (recipeStep.getVideoURL().isEmpty() && recipeStep.getThumbnailURL().isEmpty()) {
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        } else if (!recipeStep.getVideoURL().isEmpty() || !recipeStep.getThumbnailURL().isEmpty()) {
            mVideoNotAvailable.setVisibility(View.INVISIBLE);
            Bundle bundleUpVideoRelated = new Bundle();
            bundleUpVideoRelated.putParcelable(RECIPE_PARCELABLE, recipe);
            bundleUpVideoRelated.putParcelable(RECIPE_STEP, recipeStep);
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setArguments(bundleUpVideoRelated);
            getFragmentManager().beginTransaction().replace(R.id.container_for_video, videoPlayerFragment).commit();
        } else{
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        }
        /*
         * Setting the text to show the Complete Recipe Description
         */
        mRecipeStepDesc.setText(recipeStep.getDescription());

        ArrayList<RecipeStep> recipeSteps = recipe.getSteps();

        if (mTwoPane == true) {
            /**
             * Two buttons code will be here
             */
            mNextButton.setVisibility(View.VISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Timber.d("It's inside the click on previous button");
                }
            });
            mPreviousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Timber.d("It's inside the click on next button");
                }
            });
        }
        return rootView;
    }

    /**
     * TODO Need to complete this algo to save the previous and the next step
     *
     * @param recipeSteps
     * @return
     */
    public HashMap<String, RecipeStep> getPrevNextRecipes(ArrayList<RecipeStep> recipeSteps) {
        RecipeStep nextStep = recipeSteps.get(0); /* 1st item */
        RecipeStep prevStep = recipeSteps.get(recipeSteps.size() - 1); /* Last item */
        RecipeStep presentStep = recipeSteps.get(0);
        for (int i = 0; i < recipeSteps.size(); i++) {


        }
        return null;
    }
}
