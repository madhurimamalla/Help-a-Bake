package mmalla.android.com.helpabake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import timber.log.Timber;

public class RecipeSteps extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        /**
         * Retrieve the passed on recipe here
         */
        Intent previousIntent = getIntent();
        Recipe recipe = previousIntent.getParcelableExtra("Recipe_parceled");
        Timber.d("Recipe name: " + recipe.getRecipeName());
    }
}
