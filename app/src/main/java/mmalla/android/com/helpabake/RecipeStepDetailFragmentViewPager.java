package mmalla.android.com.helpabake;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import mmalla.android.com.helpabake.videoplayer.VideoPlayerFragment;

import static mmalla.android.com.helpabake.recipestep.RecipeStepDetailFragment.RECIPE_PARCELABLE;

public class RecipeStepDetailFragmentViewPager extends Fragment {

    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_STEP = "RECIPE_STEP";
    Recipe recipe;
    RecipeStep recipeStep;

    @BindView(R.id.recipe_step_detail_textview_pager)
    public TextView recipeStepFullDesc;

    @BindView(R.id.video_not_available_textview_pager)
    public TextView noVideoTextView;

    public RecipeStepDetailFragmentViewPager() {
        /**
         * Empty constructor
         */
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail_pager_item, container, false);

        ButterKnife.bind(this, view);

        recipe = getArguments().getParcelable("RECIPE_PARCEL");
        recipeStep = getArguments().getParcelable("RECIPE_STEP_PARCEL");
        String recipeStepDesc = getArguments().getString("RECIPE_STEP_DESC");

        recipeStepFullDesc.setText(recipeStepDesc);

        if (getArguments().getString("VID_URL").isEmpty()) {
            noVideoTextView.setVisibility(View.VISIBLE);
        } else {
            noVideoTextView.setVisibility(View.INVISIBLE);
            Bundle bundleUpVideoRelated = new Bundle();
            bundleUpVideoRelated.putParcelable(RECIPE_PARCELABLE, recipe);
            bundleUpVideoRelated.putParcelable(RECIPE_STEP, recipeStep);
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setArguments(bundleUpVideoRelated);
            getFragmentManager().beginTransaction().replace(R.id.container_for_video, videoPlayerFragment).commit();
        }
        return view;
    }

    public static RecipeStepDetailFragmentViewPager newInstance(String recipeStepDesc, String videoUrl, Recipe recipe, RecipeStep recipeStep) {

        RecipeStepDetailFragmentViewPager recipeStepDetailFragmentViewPager = new RecipeStepDetailFragmentViewPager();
        Bundle bundle = new Bundle();
        bundle.putString("RECIPE_STEP_DESC", recipeStepDesc);
        bundle.putString("VID_URL", videoUrl);
        bundle.putParcelable("RECIPE_PARCEL", recipe);
        bundle.putParcelable("RECIPE_STEP_PARCEL", recipeStep);
        recipeStepDetailFragmentViewPager.setArguments(bundle);
        return recipeStepDetailFragmentViewPager;
    }

}
