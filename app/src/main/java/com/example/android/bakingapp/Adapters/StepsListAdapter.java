package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.RecipesData.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepViewHolder>  {

    /*
     * Fields
     */

    private ArrayList<Step> mStepsArrayList;
    private ArrayList<Ingredient> mIngredientsArrayList;
    private Context mContext;
    private final StepOnClickHandler mStepOnClickHandler;
    private final IngredientOnClickHandler mIngredientOnClickHandler;

    /*
     * Constructor
     */

    public StepsListAdapter(Context context, ArrayList<Step> stepsArrayList,
                            ArrayList<Ingredient> ingredientArrayList, StepOnClickHandler stepAdapterOnClickHandler,
                            IngredientOnClickHandler ingredientOnClickHandler) {
        mContext = context;
        mStepsArrayList = stepsArrayList;
        mIngredientsArrayList = ingredientArrayList;
        mStepOnClickHandler = stepAdapterOnClickHandler;
        mIngredientOnClickHandler = ingredientOnClickHandler;
    }

    /*
     * Methods
     */

    @Override
    public StepsListAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // Inflate layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutIdItem = R.layout.step_item;
        boolean shouldAttachToParentImmediately = false;

        TextView view = (TextView) layoutInflater.inflate(layoutIdItem, parent, shouldAttachToParentImmediately);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListAdapter.StepViewHolder holder, int position) {

        Step step = null;

        if(position > 0) {
            step = mStepsArrayList.get(position - 1);
        }

        TextView textView = holder.mStepCardTitle;

        if(position == 0) {
            holder.setIngredientsOnClickListener(textView, mIngredientsArrayList, position);
            textView.setText("Ingredients");
        } else if (position == 1) {
            holder.setStepOnClickListener(textView, step, position);
            textView.setText("Introduction");
        } else if (position > 1 && position < getItemCount()) {
            holder.setStepOnClickListener(textView, step, position);
            textView.setText("Step " + (position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return mStepsArrayList.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        /*
         * Fields
         */

        @BindView(R.id.step_card_title) TextView mStepCardTitle;

        public StepViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        /**
         * Sets an onClickListener to the step view, passing the step instance
         * to the onClick method of the StepsListAdapter interface to further customize
         * the actions to be performed on click
         *
         * @param stepView The view on which to set the listener
         * @param step     The data that will be sent to the onClick method
         */
        private void setStepOnClickListener(TextView stepView, final Step step, final int position) {
            stepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mStepOnClickHandler.onClick(step, position);
                }
            });
        }

        /**
         * Sets an onClickListener to the step view, passing the step instance
         * to the onClick method of the StepsListAdapter interface to further customize
         * the actions to be performed on click
         *
         * @param stepView The view on which to set the listener
         * @param ingredient     The data that will be sent to the onClick method
         */
        private void setIngredientsOnClickListener(TextView stepView, final ArrayList<Ingredient> ingredient, final int position) {
            stepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIngredientOnClickHandler.onClick(ingredient, position);
                }
            });
        }
    }

    /*
     * UI interaction
     */

    /**
     * Interface to implement onClick handler for each view
     */
    public interface StepOnClickHandler {
        void onClick(Step step, int position);
    }

    /**
     * Interface to implement onClick handler for each view
     */
    public interface IngredientOnClickHandler {
        void onClick(ArrayList<Ingredient> ingredient, int position);
    }
}
