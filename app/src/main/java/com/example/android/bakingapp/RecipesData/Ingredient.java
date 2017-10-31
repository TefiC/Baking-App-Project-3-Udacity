package com.example.android.bakingapp.RecipesData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents an ingredient and its associated data
 */

public class Ingredient implements Parcelable {


    /*
     * Fields
     */

    private String mName;
    private int mQuantity;
    private String mUnit;


    /*
     * Constructors
     */

    public Ingredient(String name, int quantity, String unit) {
        mName = name;
        mQuantity = quantity;
        mUnit = unit;
    }

    private Ingredient(Parcel in) {
        mName = in.readString();
        mQuantity = in.readInt();
        mUnit = in.readString();
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
        parcel.writeInt(mQuantity);
        parcel.writeString(mUnit);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };

    /*
     * Getters
     */

    public String getIngredientName() { return mName; }
    public int getIngredientQuantity() { return mQuantity; }
    public String getIngredientUnit() { return mUnit; }

    /*
     * Setters
     */

    public void setIngredientName(String name) { mName = name; }
    public void setIngredientQuantity(int quantity) { mQuantity = quantity; }
    public void setIngredientUnit(String unit) { mUnit = unit; }
}
