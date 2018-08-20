package mmalla.android.com.helpabake;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;

/**
 * Verifies that the elements/recipes are showing up in the MainActivity
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checksRecipesAreDisplayedAndAreClickable() {

        /**
         * Matches the presence in the hierarchy but is not displayed
         */
        onView(withId(R.id.error_message))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.loading_icon))
                .check(matches(not(isDisplayed())));

        /**
         * Matches that the error message contains "This is some error."
         */
        onView(withId(R.id.error_message))
                .check(matches(withText(
                        containsString("There is some error. Try again!"))));

        /**
         * Checks for items that do not exist here
         */
        onView(withId(R.id.recipe_name))
                .check(doesNotExist());

        /**
         * This is needed as the views take some time to show up
         */
        SystemClock.sleep(1000);

        /**
         * Verify that all recipes are displayed
         */
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Yellow Cake"))));
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Brownies"))));
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Nutella Pie"))));
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Cheesecake"))));

        /**
         * Verify that all recipes are clickable
         */
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Yellow Cake"))))
                .perform(click());
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Brownies"))))
                .perform(click());
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Nutella Pie"))))
                .perform(click());
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText("Cheesecake"))))
                .perform(click());

        /**
         * Click on the first recipe and wait till it goes to the next Activity
         */
        onView(ViewMatchers.withId(R.id.recyclerview)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));
    }
}
