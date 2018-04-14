package mmalla.android.com.helpabake;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import timber.log.Timber;

public class RecipeStepDetailActivity extends AppCompatActivity {

    /**
     * TODO Flow is failing when I click on the back button here. Figure that out and fix it!
     */
    public static final String RECIPE_EXTRA_INTENT = "Recipe_parceled";
    public static final String RECIPE_PARCELABLE = "RECIPE_PARCELABLE";
    public static final String RECIPE_STEP = "RECIPE_STEP";

    @Nullable
    @BindView(R.id.recipe_step_detail_textview)
    public TextView mRecipeStepDesc;

    @BindView(R.id.video_not_available_textview)
    public TextView mVideoNotAvailable;

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
        Recipe recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
        RecipeStep recipeStep = previousIntent.getParcelableExtra(RECIPE_STEP);
        Timber.d("Recipe name: " + recipe.getRecipeName());
        Timber.d("Recipe step clicked was: " + recipeStep.getShortDescription());
        Toast.makeText(this, "recipeStep: " + recipeStep.getShortDescription(), Toast.LENGTH_SHORT);

        if (recipeStep.getVideoURL().isEmpty()) {
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        } else {
            mVideoNotAvailable.setVisibility(View.INVISIBLE);
            Bundle bundleUpVideoRelated = new Bundle();
            bundleUpVideoRelated.putParcelable(RECIPE_PARCELABLE, recipe);
            bundleUpVideoRelated.putParcelable(RECIPE_STEP, recipeStep);
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setArguments(bundleUpVideoRelated);
            getFragmentManager().beginTransaction().replace(R.id.container_for_video, videoPlayerFragment).commit();
        }
        /**
         * TODO Video isn't playing when I change the orientation of the screen
         */

        /**
         * Only for portrait mode, this textView needs to be shown
         */
        /**
         * Setting the text to show the Complete Recipe Description
         */
        mRecipeStepDesc.setText(recipeStep.getDescription());

    }

}
