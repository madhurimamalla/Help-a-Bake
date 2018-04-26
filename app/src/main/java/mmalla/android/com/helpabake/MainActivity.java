package mmalla.android.com.helpabake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipe.RecipesAdapter;
import mmalla.android.com.helpabake.retrofit.RecipeBuilder;
import mmalla.android.com.helpabake.retrofit.RecipeService;
import mmalla.android.com.helpabake.roomdatabase.RecipesDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loading_icon)
    ProgressBar mProgressBar;
    @BindView(R.id.error_message)
    TextView mErrorMessage;

    private RecipesAdapter recipesAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<Recipe> recipesList;

    private RecipesDatabase recipesDatabase;

    private static final String RECIPE_LIST_SAVE_INSTANCE = "recipe_list";
    private static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    private static final int SCALING_FACTOR = 360;
    public static final String RECIPE_NAME_FROM_WIDGET = "RECIPE_NAME";
    private String ALL_RECIPES = "ALL_RECIPES";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Set up Timber for logging
         */
        Timber.plant(new Timber.DebugTree());

        /**
         * Binding the views here
         */
        ButterKnife.bind(this);

        /**
         * Make sure this runs efficiently on all mobiles and tablets.
         * On tablets, it'll show two columns or more
         */
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = SCALING_FACTOR;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), noOfColumns);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recipesDatabase = RecipesDatabase.getDatabase(getApplicationContext());

        /**
         * Retrieve the list of recipes
         */
        if (savedInstanceState != null) {
            recipesList = savedInstanceState.getParcelableArrayList(RECIPE_LIST_SAVE_INSTANCE);
            showRecipesList(recipesList);
        } else {
            getRecipesFromNetwork();
        }

    }

    public void getRecipesFromNetwork() {
        final RecipeService recipeService = RecipeBuilder.retrieve();
        final Call<ArrayList<Recipe>> recipe = recipeService.loadRecipesFromServer();

        //SimpleIdlingResource idlingResource = (SimpleIdlingResource)((MainActivity)getActivity()).getIdlingResource();

        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Timber.d("status code: ", statusCode.toString());

                ArrayList<Recipe> recipes = response.body();
                showLoading();

                recipesList = recipes;
                showRecipesList(recipes);

                recipesDatabase.recipeDao().insertRecipes(recipes);

                for (Recipe recipe : recipes) {
                    for (int i = 0; i < recipe.getIngredients().size(); i++) {
                        recipe.getIngredients().get(i).setRecipeId(recipe.getId());
                    }

                    for (int i = 0; i < recipe.getSteps().size(); i++) {
                        recipe.getSteps().get(i).set_recipeId(recipe.getId());
                    }

                    recipesDatabase.recipeDao().insertIngredients(recipe.getIngredients());
                    recipesDatabase.recipeDao().insertSteps(recipe.getSteps());
                }
                Timber.d("The recipes are saved in the Database via Room");

                showRecipesList(recipes);

                Timber.d("The recipe list is retrieved via Retrofit!");
                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);
//                if (idlingResource != null) {
//                    idlingResource.setIdleState(true);
//                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Timber.d("http fail: ", t.getMessage());
                showErrorMessage();
            }
        });
    }

    public void showRecipesList(ArrayList<Recipe> recipesList) {
        recipesAdapter = new RecipesAdapter(this, recipesList, this);
        recyclerView.setAdapter(recipesAdapter);
        showRecipes();
    }

    public void showRecipes() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Recipe recipe) {
        /**
         * Parcel the recipe and send it over to the next RecipeStepsDetail Activity
         */
        Intent recipeStepsIntent = new Intent(this, RecipeDetailsActivity.class);
        recipeStepsIntent.putExtra(RECIPE_EXTRA_INTENT, recipe);
        startActivity(recipeStepsIntent);
    }

//    private class FetchRecipesList extends AsyncTask<String, Void, List<Recipe>> {
//
//        @Override
//        protected void onPreExecute() {
//            showLoading();
//        }
//
//        @Override
//        protected List<Recipe> doInBackground(String... strings) {
//            /**
//             * Read from a local asset file / Read from an API making a network call
//             */
//            try {
//                String jsonStr = AssetJSONFile(getResources().getString(R.string.baking_asset), getApplicationContext());
//                recipesList = RecipeDetailsUtil.getRecipesFromJson(jsonStr);
//                Timber.d("The recipe list is retrieved");
//            } catch (Exception e) {
//                e.printStackTrace();
//                showErrorMessage();
//            }
//            return recipesList;
//        }
//
//        @Override
//        protected void onPostExecute(List<Recipe> recipeList) {
//            if (recipeList != null) {
//                mErrorMessage.setVisibility(View.INVISIBLE);
//                //showRecipesList();
//            } else {
//                showLoading();
//            }
//        }
//    }

//    /**
//     * Description: Util to retrieve the list of recipes from
//     * a local assets json file
//     *
//     * @param filename
//     * @param context
//     * @return
//     * @throws IOException
//     */
//    public static String AssetJSONFile(String filename, Context context) throws IOException {
//        AssetManager manager = context.getAssets();
//        InputStream file = manager.open(filename);
//        byte[] formArray = new byte[file.available()];
//        file.read(formArray);
//        file.close();
//        return new String(formArray);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPE_LIST_SAVE_INSTANCE, recipesList);
        super.onSaveInstanceState(outState);
    }
}