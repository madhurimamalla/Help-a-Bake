package mmalla.android.com.helpabake;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import mmalla.android.com.helpabake.util.RecipeDetailsUtil;

public class MainActivity extends AppCompatActivity {

    /**
     * Create a RecyclerView to load an adapter with
     * Recipe names on it with a card view each
     */
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Recipe> recipesList;

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        try {
            String jsonStr = AssetJSONFile("baking.json", getApplicationContext());
            recipesList = RecipeDetailsUtil.getRecipesFromJson(jsonStr);
            Log.d(TAG, "The recipeList is retrieved");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recipesAdapter = new RecipesAdapter(this, recipesList);
        recyclerView.setAdapter(recipesAdapter);
    }

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }
}