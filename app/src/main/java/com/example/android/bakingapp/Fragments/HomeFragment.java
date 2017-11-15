package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.Activities.MainActivity;
import com.example.android.bakingapp.Activities.StepsListActivity;
import com.example.android.bakingapp.Adapters.RecipesMainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.Utils.NetworkUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.android.bakingapp.Activities.MainActivity.mTabletLayout;
import static com.example.android.bakingapp.Utils.NetworkUtils.RECIPES_INTERNET_LOADER_ID;
import static com.example.android.bakingapp.Utils.NetworkUtils.createNoConnectionDialog;
import static com.example.android.bakingapp.Utils.RecipeDataUtils.fillRecipesArray;

/**
 * Main Fragment to display recipes
 */

public class HomeFragment extends Fragment implements RecipesMainAdapter.RecipeAdapterOnClickHandler {

    /*
     * Views
     */

    @BindView(R.id.main_recipes_grid_layout) RecyclerView mMainListRecyclerView;
    @BindView(R.id.main_progress_bar) ProgressBar mProgressBar;

    /*
     * Fields
     */

    private RelativeLayout mRootView;
    private Unbinder unbinder;



    /*
     * Methods
     */

    /**
     * Creates an instance of the fragment
     *
     * @return A HomeFragment
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.home_fragment, container, false);

        unbinder = ButterKnife.bind(this, mRootView);

        Log.v("FRAGMENT", "CREATING FRAGMENT");

        if(savedInstanceState != null && savedInstanceState.containsKey("recipeObjects")) {
            RecipesListFragment.mRecipesArray = savedInstanceState.getParcelableArrayList("recipeObjects");
        }

        setMainActivityAdapter();
        mRootView.findViewById(R.id.main_recipes_grid_layout).setVisibility(View.VISIBLE);
//
//        // Check if there is network connection to fetch recipes data
//        if(NetworkUtils.isNetworkAvailable(getActivity())) {
//            fetchRecipesFromInternet(getActivity(), getLoaderManager());
//
//            // Display the recyclerView is there was previous data loaded, in case of rotation
//            if(RecipesListFragment.mRecipesArray.size() > 0) {
//                mMainListRecyclerView.setVisibility(View.VISIBLE);
//            }
//        // Else, if there isn't a network connection, display a dialog and a particular screen
//        } else {
//            NetworkUtils.createNoConnectionDialog(getActivity());
//            toggleNoConnectionScreen(true);
//        }

        return mRootView;
    }

    /**
     * Fetch recipes from the internet by starting the corresponding loader
     *
     * @param context       The context
     * @param loaderManager The loaderManager to determine if the loader already exists
     */
    public void fetchRecipesFromInternet(Context context, LoaderManager loaderManager) {
        Loader<String> searchLoader = loaderManager.getLoader(RECIPES_INTERNET_LOADER_ID);

        Log.v("COUNTER", "FETCHING DATA");

        if (searchLoader == null) {
            loaderManager.initLoader(RECIPES_INTERNET_LOADER_ID, null, new RecipesInternetLoader(context));
        } else {
            loaderManager.restartLoader(RECIPES_INTERNET_LOADER_ID, null, new RecipesInternetLoader(context));
        }
    }

    /**
     * Sets the Movie Adapter for the main layout that will contain movie posters
     */
    private void setMainActivityAdapter() {

        if(mMainListRecyclerView != null) {
            // Layout Manager
            setMainActivityLayoutManager(mMainListRecyclerView);

            // Create and set the adapter
            RecipesMainAdapter recipesMainAdapter = new RecipesMainAdapter(getActivity(), RecipesListFragment.mRecipesArray, this);

            if (RecipesListFragment.mRecipesArray.size() > 0) {
                mMainListRecyclerView.setAdapter(recipesMainAdapter);
            }
        }
    }

    /**
     * Sets a grid layout manager
     */
    private void setMainActivityLayoutManager(RecyclerView recyclerView) {

        if (mTabletLayout) {
            // Create and apply the layout manager
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mGridLayoutManager);
        } else {
            // Create and apply the layout manager
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }
    }

    /**
     * Toggles a No Connection Screen displayed to the user if there is no internet connection
     *
     * @param displayScreen True if the no connection screen should be displayed. False otherwise
     */
    private void toggleNoConnectionScreen(boolean displayScreen) {
        if(displayScreen) {
            mRootView.findViewById(R.id.no_connection_main_layout).setVisibility(View.VISIBLE);
        } else {
            mRootView.findViewById(R.id.no_connection_main_layout).setVisibility(View.GONE);
        }
    }

    /*
     * Lifecycle methods
     */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {

        boolean connectionAvailable = NetworkUtils.isNetworkAvailable(getActivity());

        if(RecipesListFragment.mRecipesArray.size() == 0 && connectionAvailable) {
            toggleNoConnectionScreen(false);
            fetchRecipesFromInternet(getActivity(), getLoaderManager());
        } else if(!connectionAvailable) {
            toggleNoConnectionScreen(true);
            createNoConnectionDialog(getActivity());
        }
        super.onResume();
    }

    /*
     * Interfaces Implementations
     */

    @Override
    public void onClick(Recipe recipe) {

        if(MainActivity.mTabletLayout) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("recipeObject", recipe);
            intent.putExtra("isTabletLayout", MainActivity.mTabletLayout);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), StepsListActivity.class);
            intent.putExtra("recipeObject", recipe);
            intent.putExtra("isTabletLayout", MainActivity.mTabletLayout);
            startActivity(intent);
        }
    }

    // Loader ======================================================================================

    private boolean mDataHasLoaded = false;

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
                    // If there was no previous data, fetch recipes from internet
                    if (RecipesListFragment.mRecipesArray.size() == 0 && !mDataHasLoaded) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        MainActivity.mIdlingResource.increment();
                        Log.v("COUNTER", "INCREMENT");
                        mDataHasLoaded = true;
                        forceLoad();
                    } else {
                        setMainActivityAdapter();
                        Log.v("COUNTER", "NOT LOADING");
                    }
                }

                @Override
                public String loadInBackground() {
                    try {
                        Log.v("COUNTER", "LOADING IN BACKGROUND");
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

            fillRecipesArray(getActivity(), data);
            mProgressBar.setVisibility(View.GONE);
            mMainListRecyclerView.setVisibility(View.VISIBLE);
            setMainActivityAdapter();
            MainActivity.mIdlingResource.decrement();
            Log.v("COUNTER", "DECREMENT");
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("recipeObjects", RecipesListFragment.mRecipesArray);
        super.onSaveInstanceState(outState);
    }
}
