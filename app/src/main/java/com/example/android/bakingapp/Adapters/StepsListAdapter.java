package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
     * Constants
     */

    private static final String INGREDIENTS_CARD_TITLE = "Ingredients";
    private static final String INTRODUCTION_CARD_TITLE = "Introduction";
    private static final String STEP_CARD_TITLE = "Step";


    /*
     * Fields
     */


    private Context mContext;
    private ArrayList<Step> mStepsArrayList;
    private ArrayList<Ingredient> mIngredientsArrayList;
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

        LinearLayout view = (LinearLayout) layoutInflater.inflate(layoutIdItem, parent, shouldAttachToParentImmediately);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListAdapter.StepViewHolder holder, int position) {

        Step step = null;

        // If the position is not 0, it's a step, not ingredients
        if(position > 0) {
            step = mStepsArrayList.get(position - 1);
            if(!step.getStepVideoUrl().equals("")) {
                holder.mStepCardVideo.setVisibility(View.VISIBLE);
            }
        }

        holder.mStepCardVideo.setContentDescription("step_video_logo");

        // Get the main root view
        LinearLayout mainLayout = holder.mStepCardMainLayout;

        // Get the text views
        TextView titleView = holder.mStepCardTitle;
        TextView descriptionView = holder.mStepCardDescription;

        // If it's the first item (ingredients)
        if(position == 0) {
            holder.setIngredientsOnClickListener(mainLayout, mIngredientsArrayList, position);
            titleView.setText(INGREDIENTS_CARD_TITLE);
        // Else, if it's the introduction step
        } else if (position == 1) {
            setStepItemUI(holder, mainLayout, step, position, titleView, descriptionView, INTRODUCTION_CARD_TITLE);
        // Else, if it's a generic step
        } else if (position > 1 && position < getItemCount()) {
            setStepItemUI(holder, mainLayout, step, position, titleView, descriptionView, STEP_CARD_TITLE);
        }
    }

    @Override
    public int getItemCount() {
        return mStepsArrayList.size() + 1;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        /*
         * Views
         */

        // Main layout
        @BindView(R.id.step_card_main_layout) LinearLayout mStepCardMainLayout;

        // Views
        @BindView(R.id.step_card_title) TextView mStepCardTitle;
        @BindView(R.id.step_card_description) TextView mStepCardDescription;
        @BindView(R.id.step_card_video) ImageView mStepCardVideo;
        @BindView(R.id.step_card_arrow) ImageView mStepCardArrow;

        public StepViewHolder(View itemView) {
            super(itemView);

            // Bind views
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
        private void setStepOnClickListener(LinearLayout stepView, final Step step, final int position) {
            stepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mStepOnClickHandler.onClick(step, position);
                }
            });
        }

        /**
         * Sets an onClickListener to the ingredient view, passing an ingredient instance
         * to the onClick interface of StepsListAdapter to further customize
         * the actions to be performed on click
         *
         * @param ingredientsView The view on which to set the listener
         * @param ingredient     The data that will be sent to the onClick method
         */
        private void setIngredientsOnClickListener(LinearLayout ingredientsView, final ArrayList<Ingredient> ingredient, final int position) {
            ingredientsView.setOnClickListener(new View.OnClickListener() {
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
     * Sets up the Step item's UI by adding the text and onClickListener
     *
     * @param holder The view holder that will be updated
     * @param mainLayout The main layout of the view holder
     * @param step The corresponding step
     * @param position The step position in the adapter
     * @param titleView The title's text view
     * @param descriptionView The description's text view
     * @param cardTitle The step's title String
     */
    private void setStepItemUI(StepsListAdapter.StepViewHolder holder, LinearLayout mainLayout,
                               Step step, int position, TextView titleView, TextView descriptionView,
                               String cardTitle) {
        // Set title
        setCardTitle(cardTitle, titleView, position);
        // Set onClickListener
        holder.setStepOnClickListener(mainLayout, step, position);

        // Set the description's text and visibility
        descriptionView.setText(step.getStepShortDescription());
        descriptionView.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the step card's corresponding title. If the cardTitle is INTRODUCTION_CARD_TITLE
     * it sets it to that string value. Else, if it is STEP_CARD_TITLE it adds the step number
     * to the end of the string
     *
     * @param cardTitle The card's title as a String
     * @param titleView The title's text view
     * @param position The integer representing the step's position in the adapter
     */
    private void setCardTitle(String cardTitle, TextView titleView, int position) {
        switch (cardTitle) {
            case INTRODUCTION_CARD_TITLE:
                titleView.setText(cardTitle);
                break;
            case STEP_CARD_TITLE:
                titleView.setText(STEP_CARD_TITLE + " " + (position - 1));
                break;
        }
    }

    /**
     * Interface to implement onClick handler for each Step view
     */
    public interface StepOnClickHandler {
        void onClick(Step step, int position);
    }

    /**
     * Interface to implement onClick handler for the Ingredients view
     */
    public interface IngredientOnClickHandler {
        void onClick(ArrayList<Ingredient> ingredient, int position);
    }
}
