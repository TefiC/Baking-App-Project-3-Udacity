package com.example.android.bakingapp.RecipesData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a recipe with its associated data
 */

public class Recipe implements Parcelable {

    /*
     * Fields
     */

    private String mName;
    private Ingredient[] mIngredients;
    private Step[] mSteps;
    private int mServings;
    private String mImage;


    /*
     * Constructors
     */

    public Recipe(String name, Ingredient[] ingredients, Step[] steps, int servings, String image) {
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImage = image;
    }

    private Recipe(Parcel in) {
        mName = in.readString();
        mIngredients = in.createTypedArray(Ingredient.CREATOR);
        mSteps = in.createTypedArray(Step.CREATOR);
        mServings = in.readInt();
        mImage = in.readString();
    }

    /*
     * Implementing Parcelable
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeTypedArray(mIngredients, 0);
        parcel.writeTypedArray(mSteps, 0);
        parcel.writeInt(mServings);
        parcel.writeString(mImage);
    }

    public final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };

    /*
     * Getters
     */

    public String getRecipeName() { return mName; }
    public Ingredient[] getRecipeIngredients() { return mIngredients; }
    public Step[] getRecipeSteps() { return mSteps; }
    public int getRecipeServings() { return mServings; }
    public String getRecipeImage() { return mImage; }

    /*
     * Setters
     */

    public void setRecipeName(String name) { mName = name; }
    public void setRecipeIngredients(Ingredient[] ingredients) { mIngredients = ingredients; }
    public void setRecipeSteps(Step[] steps) { mSteps = steps; }
    public void setRecipeServings(int servings) { mServings = servings; }
    public void setRecipeImage(String image) { mImage = image; }
}
