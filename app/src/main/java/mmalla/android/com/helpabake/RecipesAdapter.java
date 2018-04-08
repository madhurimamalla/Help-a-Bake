package mmalla.android.com.helpabake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> recipeList;

    public RecipesAdapter(Context context, List<Recipe> recipeList) {
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
    public void onBindViewHolder(RecipesAdapter.MyViewHolder holder, int position) {
        Recipe recipe = recipeList.get(holder.getAdapterPosition());

        TextView recipeNameTextView = (TextView) holder.mTextView.findViewById(R.id.recipe_name);

        recipeNameTextView.setText(recipe.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
