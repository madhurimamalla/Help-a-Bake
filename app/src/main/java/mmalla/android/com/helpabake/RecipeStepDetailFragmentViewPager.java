package mmalla.android.com.helpabake;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipestep.RecipeStep;

public class RecipeStepDetailFragmentViewPager extends Fragment{


    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_STEP = "RECIPE_STEP";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_detail_pager_item, container, false);

        if(getArguments().containsKey("RECIPE_EXTRA_INTENT")){
            Recipe recipe = getArguments().getParcelable(RECIPE_EXTRA_INTENT);
            RecipeStep recipeStep = getArguments().getParcelable(RECIPE_STEP);
        }

        /**
         * TODO populate rest of the content here
         */

        return v;
    }
}
