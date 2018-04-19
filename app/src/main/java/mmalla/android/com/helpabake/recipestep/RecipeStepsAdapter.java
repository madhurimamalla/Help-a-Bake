package mmalla.android.com.helpabake.recipestep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.Recipe;
import mmalla.android.com.helpabake.RecipeDetailsActivity;
import mmalla.android.com.helpabake.RecipeStepDetailActivity;
import mmalla.android.com.helpabake.RecipeStepDetailFragment;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyStepsViewHolder> {

    private ArrayList<RecipeStep> mList;
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


    public RecipeStepsAdapter(RecipeDetailsActivity mParentActivity, ArrayList<RecipeStep> mList, Recipe recipe, boolean twoPane) {
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
                RecipeStep recipeStep1 = mList.get(position);

                if (mTwoPane) {
                    Bundle bundle = new Bundle();
                    /**
                     * Initially display the first step
                     */
                    bundle.putParcelable(RECIPE_STEP, recipeStep1);
                    bundle.putParcelable(RECIPE_EXTRA_INTENT, recipe);
                    RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                    recipeStepDetailFragment.setArguments(bundle);
                    /**
                     * Calling the fragment as this is a tablet
                     */
                    mParentActivity.getFragmentManager().beginTransaction()
                            .replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment).commit();
                } else {
                    Toast.makeText(v.getContext(), recipeStep.getShortDescription(), Toast.LENGTH_SHORT).show();
                    Intent recipeStepDetailIntent = new Intent(v.getContext(), RecipeStepDetailActivity.class);
                    recipeStepDetailIntent.putExtra(RECIPE_EXTRA_INTENT, recipe);
                    recipeStepDetailIntent.putExtra(RECIPE_STEP, recipeStep1);
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
