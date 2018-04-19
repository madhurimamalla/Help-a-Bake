package mmalla.android.com.helpabake.recipestep;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.Recipe;
import mmalla.android.com.helpabake.RecipeDetailsActivity;

public class RecipeStepsFragment extends Fragment {

    public static final String RECIPE_STEP_LIST = "RECIPE_STEP_LIST";
    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";
    public ArrayList<RecipeStep> recipeSteps;
    public RecipeDetailsActivity mParentActivity;
    public Recipe recipe;
    public Context mContext;
    public boolean mTwoPane;

    public RecipeStepsFragment() {
        /**
         * Empty constructor
         */
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            recipeSteps = savedInstanceState.getParcelableArrayList(RECIPE_STEP_LIST);
            recipe = savedInstanceState.getParcelable(RECIPE_PARCELABLE);
        } else if (getArguments() != null) {
            recipeSteps = getArguments().getParcelableArrayList(RECIPE_STEP_LIST);
            recipe = getArguments().getParcelable(RECIPE_PARCELABLE);
        }

        final View rootView = inflater.inflate(R.layout.recipe_steps_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new RecipeStepsAdapter(mParentActivity, recipeSteps, recipe, mTwoPane));

        return rootView;
    }

    public void setRecipeStepList(ArrayList<RecipeStep> recipeStepList) {
        this.recipeSteps = recipeStepList;
    }

    public void setMTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public void setParentActivity(RecipeDetailsActivity parentActivity) {
        this.mParentActivity = parentActivity;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_STEP_LIST, recipeSteps);
        outState.putParcelable(RECIPE_PARCELABLE, recipe);
    }


}
