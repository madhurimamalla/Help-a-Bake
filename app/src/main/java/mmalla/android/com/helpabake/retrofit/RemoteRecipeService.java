package mmalla.android.com.helpabake.retrofit;

import java.util.List;

import mmalla.android.com.helpabake.recipe.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * RemoteRecipeService uses Retrofit to get the data from the internet
 */
public interface RemoteRecipeService {

    @GET("baking.json")
    Call<List<Recipe>> loadRecipesFromServer();
}