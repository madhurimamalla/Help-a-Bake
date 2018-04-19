package mmalla.android.com.helpabake.recipestep;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.Recipe;
import mmalla.android.com.helpabake.RecipeStepDetailActivity;

public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsOnClickListener {

    public static final String RECIPE_STEP_LIST = "RECIPE_STEP_LIST";
    public static final String RECIPE_EXTRA_INTENT = "Recipe_parceled";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";
    public ArrayList<RecipeStep> recipeSteps;
    public Recipe recipe;
    public Context mContext;

    public RecipeStepsFragment() {
        /**
         * Empty constructor
         */
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.recipeSteps = new ArrayList<RecipeStep>();
        //this.recipe = new Recipe();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //recipeSteps = new ArrayList<RecipeStep>();
        //recipe = new Recipe();

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

        recyclerView.setAdapter(new RecipeStepsAdapter(recipeSteps, this));

        return rootView;
    }

    public void setRecipeStepList(ArrayList<RecipeStep> recipeStepList){
        this.recipeSteps = recipeStepList;
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(RecipeStep recipeStep) {
        Toast.makeText(getContext(), recipeStep.getShortDescription(), Toast.LENGTH_SHORT).show();
        /**
         * TODO When it's clicked on a phone, open a new activity which shows
         * the video on top and the step description below
         * We need to also send the recipe here for sending it further
         */
        Intent recipeStepDetailIntent = new Intent(getContext(), RecipeStepDetailActivity.class);
        recipeStepDetailIntent.putExtra(RECIPE_EXTRA_INTENT, recipe);
        recipeStepDetailIntent.putExtra(RECIPE_STEP, recipeStep);
        startActivity(recipeStepDetailIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_STEP_LIST, recipeSteps);
        outState.putParcelable(RECIPE_PARCELABLE, recipe);
    }
}
