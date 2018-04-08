package mmalla.android.com.helpabake;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import mmalla.android.com.helpabake.util.RecipeDetailsUtil;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickListener {

    /**
     * Create a RecyclerView to load an adapter with
     * Recipe names on it with a card view each
     */
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private TextView mErrorMessage;
    private List<Recipe> recipesList;

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_icon);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /**
         * Retrieve the list of recipes
         */
        new FetchRecipesList().execute();
    }

    public void showRecipesList() {
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
         * TODO Write a new Activity which handles the next Details (MasterDetails fragments) page and then implement this
         */
        Toast.makeText(this, "Clicked: " + recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
    }

    private class FetchRecipesList extends AsyncTask<String, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            recyclerView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipe> doInBackground(String... strings) {
            /**
             * Read from a local asset file / Read from an API making a network call
             */
            try {
                String jsonStr = AssetJSONFile(getResources().getString(R.string.baking_asset), getApplicationContext());
                recipesList = RecipeDetailsUtil.getRecipesFromJson(jsonStr);
                Log.d(TAG, "The recipeList is retrieved");
            } catch (Exception e) {
                e.printStackTrace();
                showErrorMessage();
            }
            return recipesList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            if (recipeList != null) {
                mErrorMessage.setVisibility(View.INVISIBLE);
                showRecipesList();
            } else {
                showLoading();
            }
        }
    }

    /**
     * Description: Retrieve the list of recipes from
     * a local assets json file
     *
     * @param filename
     * @param context
     * @return
     * @throws IOException
     */
    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }
}