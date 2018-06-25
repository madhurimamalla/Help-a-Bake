package mmalla.android.com.helpabake;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipestep.RecipeStep;

public class RecipeStepDetailsFragmentPagerAdapter extends FragmentPagerAdapter{

    RecipeStep recipeStep;
    Recipe recipe;

    public RecipeStepDetailsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void recipeStep(RecipeStep recipeStep){
        this.recipeStep = recipeStep;
    }

    public void recipe(Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeStepDetailFragmentViewPager.newInstance(recipe.getSteps().get(position).getDescription(), recipe.getSteps().get(position).getVideoURL(), recipe, recipeStep);
    }

    @Override
    public int getCount() {
        return recipe.getSteps().size();
    }
}
