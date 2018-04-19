package mmalla.android.com.helpabake;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import timber.log.Timber;

public class RecipeStepDetailFragment extends Fragment {

    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";

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
        }
        Timber.d("Recipe name: " + recipe.getRecipeName());
        Timber.d("Recipe step clicked was: " + recipeStep.getShortDescription());
        Toast.makeText(getActivity(), "recipeStep: " + recipeStep.getShortDescription(), Toast.LENGTH_SHORT);

        if (recipeStep.getVideoURL().isEmpty()) {
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        } else {
            mVideoNotAvailable.setVisibility(View.INVISIBLE);
            Bundle bundleUpVideoRelated = new Bundle();
            bundleUpVideoRelated.putParcelable(RECIPE_PARCELABLE, recipe);
            bundleUpVideoRelated.putParcelable(RECIPE_STEP, recipeStep);
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setArguments(bundleUpVideoRelated);
            getFragmentManager().beginTransaction().replace(R.id.container_for_video, videoPlayerFragment).commit();
        }
        /*
         * Setting the text to show the Complete Recipe Description
         */
        mRecipeStepDesc.setText(recipeStep.getDescription());
        return rootView;
    }
}
