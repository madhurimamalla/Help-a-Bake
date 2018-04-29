package mmalla.android.com.helpabake.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.RecipeDetailsActivity;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipe.RecipeController;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<Recipe> recipes;

    Context mContext;
    Intent mIntent;

    RecipeController recipeController;


    public WidgetDataProvider(Context mContext, Intent mIntent) {
        this.mContext = mContext;
        this.mIntent = mIntent;
        this.recipeController = new RecipeController(mContext);
    }

    @Override
    public void onCreate() {
        recipes = recipeController.fetchRecipesFromCache();
    }

    @Override
    public void onDataSetChanged() {
        recipes = recipeController.fetchRecipesFromCache();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_row);

        String text = recipes.get(position).getName();

        remoteViews.setTextViewText(R.id.recipe_name, text);
        remoteViews.setTextColor(R.id.recipe_name, Color.BLACK);


        Bundle extras = new Bundle();
        extras.putInt(RecipeDetailsActivity.RECIPE_ID_FROM_WIDGET, recipes.get(position).getId());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.recipe_name, fillInIntent);

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
