package com.example.android.bakingapp.Loaders;

/**
 * Created by estef on 11/1/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.android.bakingapp.Utils.NetworkUtils;

import java.io.IOException;

/**
 * Loads the recipes
 */
public class RecipesInternetLoader implements LoaderManager.LoaderCallbacks<String> {

    private Context mContext;

    public RecipesInternetLoader(Context context) {
        mContext = context;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(mContext) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                Log.v("DATA FETCHED", "LOADING IN BACKGROUND");
                try {
                    return NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildSearchUrl(NetworkUtils.RECIPES_SEARCH_URL));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.v("DATA FETCHED", data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}