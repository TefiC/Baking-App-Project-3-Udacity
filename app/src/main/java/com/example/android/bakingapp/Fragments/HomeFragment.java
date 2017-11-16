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

    @BindView(R.id.main_recipes_grid_layout)
    RecyclerView mMainListRecyclerView;
    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;


    /*
     * Constants
     */


    private static final String RECIPE_OBJECT_INTENT_KEY = "recipeObject";
    private static final String RECIPES_ARRAYLIST_INTENT_KEY = "recipeObjects";
    private static final String IS_TABLET_LAYOUT_INTENT_KEY = "isTabletLayout";
    private static final String SCROLL_KEY_INSTANCE_STATE = "scroll";


    /*
     * Fields
     */


    private RelativeLayout mRootView;
    private Unbinder unbinder;

    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;


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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.home_fragment, container, false);

        // Bind views
        unbinder = ButterKnife.bind(this, mRootView);

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_ARRAYLIST_INTENT_KEY)) {
            RecipesListFragment.mRecipesArray = savedInstanceState.getParcelableArrayList(RECIPES_ARRAYLIST_INTENT_KEY);
        }

        // Set adapter and display the recipes
        setHomeFragmentAdapter();
        mMainListRecyclerView.setVisibility(View.VISIBLE);

        if(savedInstanceState != null && savedInstanceState.containsKey(SCROLL_KEY_INSTANCE_STATE)) {
            int position = savedInstanceState.getInt(SCROLL_KEY_INSTANCE_STATE);

            if(RecipesListFragment.mRecipesArray.size() > 0) {
                mMainListRecyclerView.smoothScrollToPosition(position);
            }
        }

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

        if (searchLoader == null) {
            loaderManager.initLoader(RECIPES_INTERNET_LOADER_ID, null, new RecipesInternetLoader(context));
        } else {
            loaderManager.restartLoader(RECIPES_INTERNET_LOADER_ID, null, new RecipesInternetLoader(context));
        }
    }

    /**
     * Sets the recipes Adapter for the main layout that will contain recipes data
     */
    private void setHomeFragmentAdapter() {

        if (mMainListRecyclerView != null) {
            // Layout Manager
            setHomeFragmentLayoutManager(mMainListRecyclerView);

            // Create and set the adapter
            RecipesMainAdapter recipesMainAdapter = new RecipesMainAdapter(getActivity(),
                    RecipesListFragment.mRecipesArray, this);

            if (RecipesListFragment.mRecipesArray.size() > 0) {
                mMainListRecyclerView.setAdapter(recipesMainAdapter);
            }
        }
    }

    /**
     * Sets a grid layout manager
     */
    private void setHomeFragmentLayoutManager(RecyclerView recyclerView) {

        if (mTabletLayout) {
            // Create and apply the layout manager
            mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mGridLayoutManager);
        } else {
            // Create and apply the layout manager
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }
    }

    /**
     * Toggles a "No Connection Screen" displayed to the user if there is no internet connection
     *
     * @param displayScreen True if the no connection screen should be displayed. False otherwise
     */
    private void toggleNoConnectionScreen(boolean displayScreen) {
        if (displayScreen) {
            mRootView.findViewById(R.id.no_connection_main_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.main_recipes_grid_layout).setVisibility(View.GONE);
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

        if (RecipesListFragment.mRecipesArray.size() == 0 && connectionAvailable) {
            toggleNoConnectionScreen(false);
            fetchRecipesFromInternet(getActivity(), getLoaderManager());
        } else if (!connectionAvailable) {
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

        Intent intent;

        if (MainActivity.mTabletLayout) {
            intent = new Intent(getActivity(), DetailsActivity.class);
        } else {
            intent = new Intent(getActivity(), StepsListActivity.class);
        }

        intent.putExtra(RECIPE_OBJECT_INTENT_KEY, recipe);
        intent.putExtra(IS_TABLET_LAYOUT_INTENT_KEY, MainActivity.mTabletLayout);
        startActivity(intent);
    }

    // Loader ======================================================================================

    private boolean mDataHasStartedLoading = false;

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
                    if (RecipesListFragment.mRecipesArray.size() == 0 && !mDataHasStartedLoading) {
                        // Show a progress bar while fetching data
                        mProgressBar.setVisibility(View.VISIBLE);

                        // For testing idling resource
                        MainActivity.mIdlingResource.increment();

                        mDataHasStartedLoading = true;
                        forceLoad();
                    } else {
                        setHomeFragmentAdapter();
                    }
                }

                @Override
                public String loadInBackground() {
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
            // Populate data
            fillRecipesArray(getActivity(), data);

            // Set views visibility
            mProgressBar.setVisibility(View.GONE);
            mMainListRecyclerView.setVisibility(View.VISIBLE);

            // Set adapter
            setHomeFragmentAdapter();

            // For testing idling resource
            MainActivity.mIdlingResource.decrement();
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Save data
        outState.putParcelableArrayList(RECIPES_ARRAYLIST_INTENT_KEY, RecipesListFragment.mRecipesArray);

        if(mTabletLayout) {
            outState.putInt(SCROLL_KEY_INSTANCE_STATE, mGridLayoutManager.findFirstVisibleItemPosition());
        } else {
            outState.putInt(SCROLL_KEY_INSTANCE_STATE, mLinearLayoutManager.findFirstVisibleItemPosition());
        }

        super.onSaveInstanceState(outState);
    }
}
