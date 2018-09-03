package mmalla.android.com.helpabake;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.ingredient.Ingredient;
import mmalla.android.com.helpabake.ingredient.IngredientsListFragment;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import mmalla.android.com.helpabake.recipestep.RecipeStepDetailFragment;
import mmalla.android.com.helpabake.recipestep.RecipeStepsFragment;
import mmalla.android.com.helpabake.roomdatabase.RecipesDatabase;
import timber.log.Timber;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_STEP = "RECIPE_STEP";
    public static final String RECIPE_ID_FROM_WIDGET = "RECIPE_ID_FROM_WIDGET";
    public static final String TWO_PANE = "TWO_PANE";
    public Recipe recipe;
    public RecipeStep recipeStep;
    private RecipesDatabase recipesDatabase;

    @BindView(R.id.recipe_name_title)
    public TextView mRecipeName;


    /**
     * A single-pane display refers to phone screens, and two-pane to larger tablet screens
     */
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipesDatabase = RecipesDatabase.getDatabase(getApplicationContext());

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_EXTRA_INTENT);
            mTwoPane = savedInstanceState.getBoolean(TWO_PANE);
            Timber.d("Restoring the previous information!...");
        } else {
            /**
             * Retrieve the passed on recipe here
             */
            Intent previousIntent = getIntent();
            if(previousIntent.getExtras().containsKey(RECIPE_EXTRA_INTENT)){
                recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
            }
            else if(previousIntent.getExtras().containsKey(RECIPE_ID_FROM_WIDGET)){
                /**
                 * Flow when the app is lauched from the widget
                 */
                int id = previousIntent.getExtras().getInt(RECIPE_ID_FROM_WIDGET);
                recipe = recipesDatabase.recipeDao().getRecipe(id);
                recipe.setIngredients((ArrayList<Ingredient>) recipesDatabase.recipeDao().getIngredients(id));
                recipe.setSteps((ArrayList<RecipeStep>) recipesDatabase.recipeDao().getSteps(id));

            }
            Timber.d("Recipe name: " + recipe.getName());
            Toast.makeText(this, "Recipe clicked is " + recipe.getName(), Toast.LENGTH_SHORT).show();
        }

        if (findViewById(R.id.linear_layout_to_verify) != null) {
            mTwoPane = true;
            Timber.d("This a tablet!");
        }
        getDetails(savedInstanceState);
    }

    private void getDetails(Bundle savedInstanceState) {
        /**
         * Setting recipe name here
         */
        mRecipeName.setText(recipe.getName());

        List<Ingredient> ingredients = recipe.getIngredients();

        IngredientsListFragment ingredientsListFragment = (IngredientsListFragment)getFragmentManager().findFragmentById(R.id.ingredient_fragment_container);
        if(ingredientsListFragment == null) {
            ingredientsListFragment = new IngredientsListFragment();
            ingredientsListFragment.setIngredientsList(ingredients);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.ingredient_fragment_container, ingredientsListFragment);
            fragmentTransaction.commit();
        }

        List<RecipeStep> recipeSteps = recipe.getSteps();

        RecipeStepsFragment recipeStepsFragment = (RecipeStepsFragment)getFragmentManager()
                .findFragmentById(R.id.recipe_steps_fragment_container);

        if(recipeStepsFragment == null) {
            recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setRecipeStepList(recipeSteps);
            recipeStepsFragment.setRecipe(recipe);
            recipeStepsFragment.setMTwoPane(mTwoPane);
            recipeStepsFragment.setParentActivity(this);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_steps_fragment_container, recipeStepsFragment);
            transaction.commit();
        }

        if(mTwoPane == true){
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_EXTRA_INTENT, recipe);
            /**
             * Initially display the first step
             */
            recipeStep = recipe.getSteps().get(0);
            bundle.putParcelable(RECIPE_STEP, recipeStep);
            bundle.putBoolean(TWO_PANE, true);
            RecipeStepDetailFragment recipeStepDetailFragment = (RecipeStepDetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.recipe_step_detail_fragment);
            if(recipeStepDetailFragment == null) {
                recipeStepDetailFragment = new RecipeStepDetailFragment();
                recipeStepDetailFragment.setArguments(bundle);
                /**
                 * Calling the fragment as this is a tablet
                 */
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("Inside onSaveInstanceState() Saving state ....");
        outState.putParcelable(RECIPE_EXTRA_INTENT, recipe);
        outState.putBoolean(TWO_PANE, mTwoPane);
    }
}