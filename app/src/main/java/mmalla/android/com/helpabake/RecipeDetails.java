package mmalla.android.com.helpabake;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import mmalla.android.com.helpabake.ingredient.Ingredient;
import mmalla.android.com.helpabake.ingredient.IngredientsListFragment;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import mmalla.android.com.helpabake.recipestep.RecipeStepsFragment;
import timber.log.Timber;

public class RecipeDetails extends AppCompatActivity {

    public static final String RECIPE_EXTRA_INTENT = "Recipe_parceled";
    public Recipe recipe;

    /**
     * A single-pane display refers to phone screens, and two-pane to larger tablet screens
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

//        if (findViewById(R.id.linear_layout_to_verify) != null) {
//            mTwoPane = true;
//            Timber.d("This a tablet!");
//            getDetails();
//            displayVideoOfRecipeStep();
//        } else {
//            mTwoPane = false;
//        }

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_EXTRA_INTENT);
            Timber.d("Restoring the previous information!...");
        } else {
            /**
             * Retrieve the passed on recipe here
             */
            Intent previousIntent = getIntent();
            recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
            Toast.makeText(this, "Recipe clicked is " + recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
        }
        Timber.d("Recipe name: " + recipe.getRecipeName());
        getDetails();
    }

//    public void displayVideoOfRecipeStep() {
//        Timber.d("Inside getRecipeStepDetails()...");
//
//    }

    public void getDetails() {
        ArrayList<Ingredient> ingredients = recipe.getIngredients();

        IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
        ingredientsListFragment.setIngredientsList(ingredients);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ingredient_fragment_container, ingredientsListFragment);
        fragmentTransaction.commit();

        ArrayList<RecipeStep> recipeSteps = recipe.getSteps();

        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setRecipeStepList(recipeSteps);
        recipeStepsFragment.setRecipe(recipe);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.recipe_steps_fragment_container, recipeStepsFragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("Inside onSaveInstanceState() Saving state ....");
        outState.putParcelable(RECIPE_EXTRA_INTENT, recipe);
    }
}