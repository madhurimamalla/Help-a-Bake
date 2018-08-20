package mmalla.android.com.helpabake;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> recipeDetailActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkTheRecipeSteps() {

        /**
         * This is needed as the views take some time to show up
         */
        SystemClock.sleep(1000);

        /**
         * Clicking on the first recipe
         */
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));

        /**
         * The first recipe on the list is Nutella Pie
         */
        onView(withId(R.id.recipe_name_title)).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.recipe_detail_ingredients_title)).check(matches(withText("Ingredients list")));

        /**
         * Clicking on a recipe step to see it going to the RecipeStepDetailActivity
         */
        onView(withId(R.id.recipe_steps_recyclerview))
                .check(matches(hasDescendant(withText("Recipe Introduction"))));
        onView(withId(R.id.recipe_steps_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

}
