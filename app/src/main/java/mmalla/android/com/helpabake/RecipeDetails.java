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
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String INGREDIENTS_LIST = "INGREDIENTS_LIST";
    public static final String RECIPE_STEP_LIST = "RECIPE_STEP_LIST";
    public Recipe recipe;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_EXTRA_INTENT);
            Timber.d("Recipe name: " + recipe.getRecipeName());
            Timber.d("Restoring the previous information!...");
            Timber.d("Inside onRestoreInstanceState()...");
        }
        getDetails();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        /**
         * Retrieve the passed on recipe here
         */
        Intent previousIntent = getIntent();
        recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
        Toast.makeText(this, "Recipe clicked is " + recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
        Timber.d("Recipe name: " + recipe.getRecipeName());
        Timber.d("In onCreate()....");
        getDetails();
    }

    public void getDetails() {
        ArrayList<Ingredient> ingredients = recipe.getIngredients();

        Bundle bundleOfIngredients = new Bundle();
        bundleOfIngredients.putParcelableArrayList(INGREDIENTS_LIST, ingredients);
        bundleOfIngredients.putParcelable(RECIPE_PARCELABLE, recipe);
        IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
        ingredientsListFragment.setArguments(bundleOfIngredients);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ingredient_fragment_container, ingredientsListFragment);
        fragmentTransaction.commit();

        ArrayList<RecipeStep> recipeSteps = recipe.getSteps();

        Bundle bundleOfRecipeSteps = new Bundle();
        bundleOfRecipeSteps.putParcelableArrayList(RECIPE_STEP_LIST, recipeSteps);
        bundleOfRecipeSteps.putParcelable(RECIPE_PARCELABLE, recipe);
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setArguments(bundleOfRecipeSteps);
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