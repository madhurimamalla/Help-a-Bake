package mmalla.android.com.helpabake.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mmalla.android.com.helpabake.R;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> recipeList;
    private final RecipesAdapter.RecipesAdapterOnClickListener mListener;


    public interface RecipesAdapterOnClickListener {
        void onClick(Recipe recipe);
    }

    public RecipesAdapter(Context context, List<Recipe> recipeList, RecipesAdapter.RecipesAdapterOnClickListener listener) {
        mListener = listener;
        this.recipeList = recipeList;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyViewHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.recipe_name);
        }
    }

    @Override
    public RecipesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.MyViewHolder holder, final int position) {
        Recipe recipe = recipeList.get(holder.getAdapterPosition());

        TextView recipeNameTextView = (TextView) holder.mTextView.findViewById(R.id.recipe_name);
        recipeNameTextView.setText(recipe.getName());
        recipeNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = recipeList.get(position);
                mListener.onClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
