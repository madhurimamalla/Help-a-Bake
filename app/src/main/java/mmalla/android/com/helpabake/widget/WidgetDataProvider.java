package mmalla.android.com.helpabake.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.RecipeDetailsActivity;
import mmalla.android.com.helpabake.ingredient.Ingredient;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipe.RecipeController;
import timber.log.Timber;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<Recipe> recipes;
    Recipe recipe;

    Context mContext;
    Intent mIntent;

    /**
     * Requirement states that we should display one recipe's ingredients so this number can
     * be changed for showing any other recipe or we can later implement a way to generate a
     * random number and that would show a random recipe's ingredient list.
     */
    int RECIPE_NUMBER = 0;
    public static String SHARED_PREF_RECIPE_NO = "SHARED_PREF_RECIPE_NO";
    public static String SHARED_PREF_WIDGET_RELATED = "SHARED_PREF_WIDGET_RELATED";

    RecipeController recipeController;

    public WidgetDataProvider(Context mContext, Intent mIntent) {
        this.mContext = mContext;
        this.mIntent = mIntent;
        this.recipeController = new RecipeController(mContext);
    }

    @Override
    public void onCreate() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_WIDGET_RELATED, 0);
        recipes = recipeController.fetchRecipesFromCache();
        RECIPE_NUMBER = sp.getInt(SHARED_PREF_RECIPE_NO, 0);
        /**
         * Getting the id for retrieving the recipe
         */
        int id = recipeController.fetchRecipeIdFromRecipeNo(RECIPE_NUMBER);
        recipe = recipeController.fetchRecipeFromCache(id);
        recipe.setIngredients(recipeController.fetchRecipeIngredientsFromRecipe(id));
        Timber.d("Recipe displayed on widget is : " + recipe.getName());
    }

    @Override
    public void onDataSetChanged() {
        recipes = recipeController.fetchRecipesFromCache();
        /**
         * Getting the id for retrieving the recipe
         */
        int id = recipeController.fetchRecipeIdFromRecipeNo(RECIPE_NUMBER);
        recipe = recipeController.fetchRecipeFromCache(id);
        recipe.setIngredients(recipeController.fetchRecipeIngredientsFromRecipe(id));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_row);

        Ingredient ingredient = recipe.getIngredients().get(position);

        remoteViews.setTextViewText(R.id.ingredient_name, ingredient.getIngredient());
        remoteViews.setTextColor(R.id.ingredient_name, Color.BLACK);

        remoteViews.setTextViewText(R.id.quantity, (Double.toString(ingredient.getQuantity())));
        remoteViews.setTextColor(R.id.quantity, Color.BLACK);

        remoteViews.setTextViewText(R.id.measurement, ingredient.getMeasure());
        remoteViews.setTextColor(R.id.measurement, Color.BLACK);

        /**
         * Implement an fillInIntent which opens the recipe on clicking any ingredient
         */
        Bundle extras = new Bundle();
        extras.putInt(RecipeDetailsActivity.RECIPE_ID_FROM_WIDGET, recipe.getId());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.ingredient_name, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
