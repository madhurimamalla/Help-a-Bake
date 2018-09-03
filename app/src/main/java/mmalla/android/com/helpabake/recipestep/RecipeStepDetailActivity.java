package mmalla.android.com.helpabake.recipestep;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.RecipeDetailsActivity;
import mmalla.android.com.helpabake.recipe.Recipe;
import timber.log.Timber;

/**
 * @author mmalla
 * Description: Shows the recipe step video/instructions on the screen
 */
public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";

    Recipe recipe;
    RecipeStep recipeStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Bind the views using ButterKnife
         */
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_PARCELABLE);
            recipeStep = savedInstanceState.getParcelable(RECIPE_STEP);
        } else {
            Intent previousIntent = getIntent();
            recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
            recipeStep = previousIntent.getParcelableExtra(RECIPE_STEP);
        }
        Timber.d("Recipe name: " + recipe.getName());
        Timber.d("Recipe step clicked was: " + recipeStep.getShortDescription());
        Toast.makeText(this, "recipeStep: " + recipeStep.getShortDescription(),
                Toast.LENGTH_SHORT);

        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_STEP, recipeStep);
        bundle.putParcelable(RECIPE_EXTRA_INTENT, recipe);
        RecipeStepDetailFragment recipeStepDetailFragment = (RecipeStepDetailFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.recipe_step_detail_fragment);

        if (recipeStepDetailFragment == null) {
            recipeStepDetailFragment = new RecipeStepDetailFragment();
        }
        recipeStepDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment).commit();
    }

    /**
     * Description: Takes the user back to the recipe page with steps and ingredients
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
            intent.putExtra(RECIPE_EXTRA_INTENT, recipe);
            intent.putExtra(RECIPE_STEP, recipeStep);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_PARCELABLE, recipe);
        outState.putParcelable(RECIPE_STEP, recipeStep);
    }
}
