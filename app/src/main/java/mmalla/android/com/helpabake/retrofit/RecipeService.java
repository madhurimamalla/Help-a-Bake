package mmalla.android.com.helpabake.retrofit;

import java.util.ArrayList;

import mmalla.android.com.helpabake.recipe.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * RecipeService uses Retrofit to get the data from the internet
 */
public interface RecipeService {

    @GET("baking.json")
    Call<ArrayList<Recipe>> loadRecipesFromServer();
}