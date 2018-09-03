package mmalla.android.com.helpabake.recipe;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import mmalla.android.com.helpabake.ingredient.Ingredient;
import mmalla.android.com.helpabake.recipestep.RecipeStep;
import mmalla.android.com.helpabake.retrofit.RecipeBuilder;
import mmalla.android.com.helpabake.retrofit.RemoteRecipeService;
import mmalla.android.com.helpabake.roomdatabase.RecipesDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RecipeController {

    private RecipesDatabase recipesDatabase;

    public RecipeController(Context context) {
        recipesDatabase = RecipesDatabase.getDatabase(context);
    }

    public static interface FetchRecipesCallback {

        void onSuccess(List<Recipe> recipes);

        void onFailure(Throwable t);
    }

    public void fetchRecipes(final FetchRecipesCallback cb) {


        final RemoteRecipeService remoteRecipeService = RecipeBuilder.retrieve();
        final Call<List<Recipe>> recipeFetchTask = remoteRecipeService.loadRecipesFromServer();

        recipeFetchTask.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Integer statusCode = response.code();
                Timber.d("status code: ", statusCode.toString());

                List<Recipe> recipesList = response.body();

                for (Recipe recipe : recipesList) {
                    sanitize(recipe);
                }

                recipesDatabase.recipeDao().insertRecipes(recipesList);

                for (Recipe recipe : recipesList) {
                    for (int i = 0; i < recipe.getIngredients().size(); i++) {
                        recipe.getIngredients().get(i).setRecipeId(recipe.getId());
                    }

                    for (int i = 0; i < recipe.getSteps().size(); i++) {
                        recipe.getSteps().get(i).set_recipeId(recipe.getId());
                    }

                    recipesDatabase.recipeDao().insertIngredients(recipe.getIngredients());
                    recipesDatabase.recipeDao().insertSteps(recipe.getSteps());
                }
                Timber.d("The recipes are persisted");
                cb.onSuccess(recipesList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.d("http fail: ", t.getMessage());
                cb.onFailure(t);
            }
        });
    }

    private void sanitize(Recipe r) {
        /**
         *  Verify the RecipeStep Url isn't a Malformed URL
         *  If it is, save an empty string instead
         * */
        for (RecipeStep recipeStep : r.getSteps()) {
            try {
                URL videoURL = new URL(recipeStep.getVideoURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Timber.d("Setting the VideoUrl to be an empty string if it's a Malformed URL");
                recipeStep.setVideoURL("");
            }
        }

        /**
         *  Capitalize the first letter of an Ingredient
         */
        for (Ingredient ingredient : r.getIngredients()) {
            String ingredientText = ingredient.getIngredient().toString();
            ingredient.setIngredient(StringUtils.capitalize(ingredientText));
        }
    }

    public List<Recipe> fetchRecipesFromCache() {
        return recipesDatabase.recipeDao().getRecipes();
    }

    public Recipe fetchRecipeFromCache(int id){
        return recipesDatabase.recipeDao().getRecipe(id);
    }

    public List<Ingredient> fetchRecipeIngredientsFromRecipe(int id){
        return recipesDatabase.recipeDao().getIngredients(id);
    }

    public int fetchRecipeIdFromRecipeNo(int recipeNo){
        return recipesDatabase.recipeDao().getRecipe(recipeNo).getId();
    }
}
