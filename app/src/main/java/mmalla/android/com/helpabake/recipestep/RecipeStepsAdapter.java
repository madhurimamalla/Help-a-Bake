package mmalla.android.com.helpabake.recipestep;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyStepsViewHolder> {

    private ArrayList<RecipeStep> mList;
    private final RecipeStepsAdapter.RecipeStepsOnClickListener mListener;


    public interface RecipeStepsOnClickListener {
        void onClick(RecipeStep recipeStep);
    }

    public class MyStepsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeStepDesc) TextView mShortDesc;

        public MyStepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public RecipeStepsAdapter(ArrayList<RecipeStep> mList, RecipeStepsAdapter.RecipeStepsOnClickListener listener) {
        this.mList = mList;
        mListener = listener;
    }


    @Override
    public MyStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_list_item, parent, false);

        return new MyStepsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyStepsViewHolder holder, final int position) {
        RecipeStep recipeStep = mList.get(holder.getAdapterPosition());

        TextView mShortDesc = (TextView) holder.mShortDesc.findViewById(R.id.recipeStepDesc);

        mShortDesc.setText(recipeStep.getShortDescription());

        mShortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeStep recipeStep1 = mList.get(position);
                mListener.onClick(recipeStep1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
