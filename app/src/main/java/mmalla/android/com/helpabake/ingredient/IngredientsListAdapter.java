package mmalla.android.com.helpabake.ingredient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmalla.android.com.helpabake.R;
import timber.log.Timber;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.MyViewHolder> {

    private ArrayList<Ingredient> mList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.quantity)
        TextView mQuantityTextView;
        @BindView(R.id.measure)
        TextView mMeasureTextView;
        @BindView(R.id.ingredient)
        TextView mIngredientTextView;

        public MyViewHolder(View view) {
            super(view);
            /**
             * Binding the views here
             */
            ButterKnife.bind(this, view);
            Timber.d("Bound the views and MyViewHolder is created.");
        }
    }

    public IngredientsListAdapter(ArrayList<Ingredient> list, Context context) {
        mList = list;
        mContext = context;
    }


    /**
     * Description: Creating a View Holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public IngredientsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);
        Timber.d("Returning a newly created ViewHolder");
        return new MyViewHolder(view);
    }

    /**
     * Description: Binding the data to the views
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(IngredientsListAdapter.MyViewHolder holder, int position) {
        Ingredient ingredient = mList.get(holder.getAdapterPosition());

        TextView mQuantityTV = (TextView) holder.mQuantityTextView.findViewById(R.id.quantity);
        mQuantityTV.setText(Integer.toString(ingredient.getQuantity()));

        TextView mMeasureTV = (TextView) holder.mMeasureTextView.findViewById(R.id.measure);
        mMeasureTV.setText(ingredient.getMeasure());

        TextView mIngredient = (TextView) holder.mIngredientTextView.findViewById(R.id.ingredient);
        mIngredient.setText(ingredient.getIngredient());

        Timber.d("Set up the Quantity as: " + mQuantityTV + " and Measure: " + mMeasureTV
                + " . Finally, the ingredient is: " + mIngredient + ".");
    }

    /**
     * Description: Returning the number of the recipe steps in the list
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
