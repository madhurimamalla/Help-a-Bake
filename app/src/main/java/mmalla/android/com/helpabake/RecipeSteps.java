package mmalla.android.com.helpabake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import timber.log.Timber;

public class RecipeSteps extends Activity {

    public static final String RECIPE_EXTRA_INTENT = "Recipe_parceled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        /**
         * Retrieve the passed on recipe here
         */
        Intent previousIntent = getIntent();
        Recipe recipe = previousIntent.getParcelableExtra(RECIPE_EXTRA_INTENT);
        Timber.d("Recipe name: " + recipe.getRecipeName());
    }
}
