package mmalla.android.com.helpabake;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import timber.log.Timber;

public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";

    @Nullable
    @BindView(R.id.recipe_step_detail_textview)
    public TextView mRecipeStepDesc;

    @BindView(R.id.video_not_available_textview)
    public TextView mVideoNotAvailable;

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

        Intent previousIntent = getIntent();
        recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
        recipeStep = previousIntent.getParcelableExtra(RECIPE_STEP);
        Timber.d("Recipe name: " + recipe.getRecipeName());
        Timber.d("Recipe step clicked was: " + recipeStep.getShortDescription());
        Toast.makeText(this, "recipeStep: " + recipeStep.getShortDescription(), Toast.LENGTH_SHORT);

        if (recipeStep.getVideoURL().isEmpty()) {
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        } else {
            mVideoNotAvailable.setVisibility(View.INVISIBLE);
            Bundle bundleUpVideoRelated = new Bundle();
            //bundleUpVideoRelated.putParcelable(RECIPE_PARCELABLE, recipe);
            bundleUpVideoRelated.putParcelable(RECIPE_STEP, recipeStep);
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setArguments(bundleUpVideoRelated);
            getFragmentManager().beginTransaction().replace(R.id.container_for_video, videoPlayerFragment).commit();
        }

        /**
         * Setting the text to show the Complete Recipe Description
         */
        mRecipeStepDesc.setText(recipeStep.getDescription());
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
}
