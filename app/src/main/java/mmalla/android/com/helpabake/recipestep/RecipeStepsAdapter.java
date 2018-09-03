package mmalla.android.com.helpabake.recipestep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.RecipeDetailsActivity;
import mmalla.android.com.helpabake.recipe.Recipe;
import timber.log.Timber;

import static mmalla.android.com.helpabake.recipestep.RecipeStepDetailFragment.TWO_PANE;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyStepsViewHolder> {

    private List<RecipeStep> mList;
    private Recipe recipe;
    private boolean mTwoPane;
    public static final String RECIPE_STEP = "RECIPE_STEP";
    public static final String RECIPE_EXTRA_INTENT = "RECIPE_EXTRA_INTENT";
    private final RecipeDetailsActivity mParentActivity;

    public class MyStepsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeStepDesc)
        TextView mShortDesc;

        public MyStepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public RecipeStepsAdapter(RecipeDetailsActivity mParentActivity, List<RecipeStep> mList, Recipe recipe, boolean twoPane) {
        this.mList = mList;
        this.recipe = recipe;
        this.mTwoPane = twoPane;
        this.mParentActivity = mParentActivity;

    }


    @Override
    public MyStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_list_item, parent, false);

        return new MyStepsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyStepsViewHolder holder, final int position) {
        final RecipeStep recipeStep = mList.get(holder.getAdapterPosition());

        TextView mShortDesc = (TextView) holder.mShortDesc.findViewById(R.id.recipeStepDesc);

        mShortDesc.setText(recipeStep.getShortDescription());

        mShortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeStep recipeStep = mList.get(position);

                if (mTwoPane) {
                    Bundle bundle = new Bundle();
                    /**
                     * Initially display the first step
                     */
                    bundle.putParcelable(RECIPE_STEP, recipeStep);
                    bundle.putParcelable(RECIPE_EXTRA_INTENT, recipe);
                    bundle.putBoolean(TWO_PANE, true);
                    /**
                     * Calling the fragment as this is a tablet
                     */
                    Timber.d("Initiating and replacing a Step Detail Fragment");
                    Log.d("ItemListActivity", "Initiating a fragment");
                    RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                    recipeStepDetailFragment.setArguments(bundle);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment)
                            .commit();
                } else {
                    /**
                     * If it's a phone, create a RecipeStepDetailActivity
                     */
                    Toast.makeText(v.getContext(), recipeStep.getShortDescription(),
                            Toast.LENGTH_SHORT).show();
                    Intent recipeStepDetailIntent = new Intent(v.getContext(),
                            RecipeStepDetailActivity.class);
                    recipeStepDetailIntent.putExtra(RECIPE_EXTRA_INTENT, recipe);
                    recipeStepDetailIntent.putExtra(TWO_PANE, false);
                    recipeStepDetailIntent.putExtra(RECIPE_STEP, recipeStep);

                    v.getContext().startActivity(recipeStepDetailIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
