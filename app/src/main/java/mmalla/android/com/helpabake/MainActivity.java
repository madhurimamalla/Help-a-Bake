package mmalla.android.com.helpabake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipe.RecipeController;
import mmalla.android.com.helpabake.recipe.RecipesAdapter;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loading_icon)
    ProgressBar mProgressBar;
    @BindView(R.id.error_message)
    TextView mErrorMessage;

    private RecipesAdapter recipesAdapter;
    private GridLayoutManager mLayoutManager;
    private List<Recipe> recipesList = new ArrayList<Recipe>();

    private RecipeController controller;

    private static final String RECIPE_LIST_SAVE_INSTANCE = "recipe_list";
    private static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    private static final int SCALING_FACTOR = 360;
    private String ALL_RECIPES = "ALL_RECIPES";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Set up Timber for logging
         */
        Timber.plant(new Timber.DebugTree());

        /**
         * Binding the views here
         */
        ButterKnife.bind(this);

        /**
         * Make sure this runs efficiently on all mobiles and tablets.
         * On tablets, it'll show two columns or more
         */
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = SCALING_FACTOR;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), noOfColumns);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        controller = new RecipeController(getApplicationContext());

        /**
         * Retrieve the list of recipes
         */
        if (savedInstanceState != null) {
            Timber.d("Main activity: Saved state found !!!");
            recipesList.clear();
            recipesList.addAll(savedInstanceState.<Recipe> getParcelableArrayList(RECIPE_LIST_SAVE_INSTANCE));
            showRecipesList(recipesList);
        } else {
            Timber.d("Main activity: No saved state !!!");
            controller.fetchRecipes(new RecipeController.FetchRecipesCallback() {

                @Override
                public void onSuccess(List<Recipe> recipes) {
                    recipesList.addAll(recipes);
                    showRecipesList(recipes);
                }

                @Override
                public void onFailure(Throwable t) {
                    showErrorMessage(t.getMessage());
                }
            });
        }
    }

    public void showRecipesList(List<Recipe> recipesList) {
        recipesAdapter = new RecipesAdapter(this, recipesList, this);
        recyclerView.setAdapter(recipesAdapter);
        showRecipes();
    }

    public void showRecipes() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String errorText) {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mErrorMessage.setText(errorText);
    }

    private void showLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Recipe recipe) {
        /**
         * Parcel the recipe and send it over to the next RecipeStepsDetail Activity
         */
        Intent recipeStepsIntent = new Intent(this, RecipeDetailsActivity.class);
        recipeStepsIntent.putExtra(RECIPE_EXTRA_INTENT, recipe);
        startActivity(recipeStepsIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
            outState.putParcelableArrayList(RECIPE_LIST_SAVE_INSTANCE, new ArrayList<Recipe>(recipesList));
            super.onSaveInstanceState(outState);
    }
}