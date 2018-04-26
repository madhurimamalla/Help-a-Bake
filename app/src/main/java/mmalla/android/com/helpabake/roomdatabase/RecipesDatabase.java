package mmalla.android.com.helpabake.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import mmalla.android.com.helpabake.ingredient.Ingredient;
import mmalla.android.com.helpabake.recipe.Recipe;
import mmalla.android.com.helpabake.recipestep.RecipeStep;

@Database(entities = {Recipe.class, Ingredient.class, RecipeStep.class}, version = 1)
public abstract class RecipesDatabase extends RoomDatabase {

    private static RecipesDatabase INSTANCE;

    public abstract RecipeDao recipeDao();

    public static RecipesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, RecipesDatabase.class, RecipesDbContract.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}