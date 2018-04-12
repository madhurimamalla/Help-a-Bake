package mmalla.android.com.helpabake.recipestep;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mmalla.android.com.helpabake.R;

public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsOnClickListener {

    public static final String RECIPE_STEP_LIST = "RECIPE_STEP_LIST";
    public ArrayList<RecipeStep> recipeSteps;
    public Context mContext;

    public RecipeStepsFragment() {
        /**
         * Empty constructor
         */
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        recipeSteps = new ArrayList<RecipeStep>();

        if (getArguments() != null) {
            recipeSteps = getArguments().getParcelableArrayList(RECIPE_STEP_LIST);
        }

        final View rootView = inflater.inflate(R.layout.recipe_steps_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new RecipeStepsAdapter(recipeSteps, this));

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(RecipeStep recipeStep) {
        Toast.makeText(getContext(), recipeStep.getShortDescription(), Toast.LENGTH_SHORT).show();
    }
}
