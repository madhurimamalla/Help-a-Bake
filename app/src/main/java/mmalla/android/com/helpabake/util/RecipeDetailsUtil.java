package mmalla.android.com.helpabake.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mmalla.android.com.helpabake.Ingredient;
import mmalla.android.com.helpabake.Recipe;
import mmalla.android.com.helpabake.RecipeStep;

public class RecipeDetailsUtil {

    private static final String TAG = RecipeDetailsUtil.class.getSimpleName();
    /**
     * Related to the recipe
     */
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";

    /**
     * Related to each of the ingredients
     */
    private static final String INGREDIENTS_QUANTITY = "quantity";
    private static final String INGREDIENTS_MEASURE = "measure";
    private static final String INGREDIENTS_INGREDIENT = "ingredient";

    /**
     * Related to each of the recipe steps
     */
    private static final String STEPS_ID = "id";
    private static final String STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEPS_DESCRIPTION = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";

    public static ArrayList<Recipe> getRecipesFromJson(String recipesJsonStr) throws JSONException {
        Log.d("Json String: ", recipesJsonStr);
        JSONArray array = new JSONArray(recipesJsonStr);
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        for (int i = 0; i < array.length(); i++) {
            //Recipe recipe = new Recipe();
            JSONObject object = (JSONObject) array.getJSONObject(i);
            /**
             * Get and set the ID of the recipe
             */
            int id = object.getInt(RECIPE_ID);
            Log.d(TAG, String.valueOf(id));
            //recipe.setId(id);
            /**
             * Get and set the name of the recipe
             */
            String name = object.getString(RECIPE_NAME);
            Log.d(TAG, name);
            //recipe.setRecipeName(name);
            /**
             * Get and set the servings of the recipe
             */
            int servings = object.getInt(RECIPE_SERVINGS);
            Log.d(TAG, String.valueOf(servings));
            //recipe.setServings(servings);
            /**
             * Get and set the image of the recipe
             */
            String image = object.getString(RECIPE_IMAGE);
            Log.d(TAG, image);
//            if (image.equals("")) {
//                recipe.setImage("");
//            }

            List<Ingredient> ingredientList = new ArrayList<Ingredient>();
            /**
             * Go through the ingredients list and save them to the recipe
             */
            JSONArray ingredientsArray = object.getJSONArray(RECIPE_INGREDIENTS);
            /**
             * TODO Add a verification and check if there are no ingredients and add a note
             */
            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject ingredientObj = (JSONObject) ingredientsArray.getJSONObject(i);
                String measure = ingredientObj.getString(INGREDIENTS_MEASURE);
                int quantity = ingredientObj.getInt(INGREDIENTS_QUANTITY);
                String ingredient = ingredientObj.getString(INGREDIENTS_INGREDIENT);
                Ingredient ingredient1 = new Ingredient(quantity, measure, ingredient);
                /**
                 * Added to the list of Ingredients
                 */
                ingredientList.add(ingredient1);
            }

            List<RecipeStep> recipeStepList = new ArrayList<RecipeStep>();
            /**
             * Go through the recipe's steps and save them to the recipe
             */
            JSONArray stepsArray = object.getJSONArray(RECIPE_STEPS);
            /**
             * TODO Add a verification to check if there are no recipe steps and add a note
             */
            for(int j = 0; j< stepsArray.length(); j++){
                JSONObject stepObj = (JSONObject) stepsArray.getJSONObject(j);
                int idOfStep = stepObj.getInt(STEPS_ID);
                String shortDesc = stepObj.getString(STEPS_SHORT_DESCRIPTION);
                String desc = stepObj.getString(STEPS_DESCRIPTION);
                String vidUrl = stepObj.getString(STEPS_VIDEO_URL);
                String thumbnailUrl = stepObj.getString(STEPS_THUMBNAIL_URL);
                RecipeStep recipeStep = new RecipeStep(idOfStep, shortDesc, desc, vidUrl, thumbnailUrl);
                /**
                 * Added the recipe step to the list of recipe steps
                 */
                recipeStepList.add(recipeStep);
            }
            Recipe recipe1 = new Recipe(id, name, ingredientList, recipeStepList, servings, image);
            /**
             * Add the recipe to the list of recipes
             */
            recipeList.add(recipe1);
        }
        return recipeList;
    }
}
