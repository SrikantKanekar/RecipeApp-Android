package com.example.recipeapp.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.AppExecutors;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.requests.responses.RecipeResponse;
import com.example.recipeapp.requests.responses.RecipeSearchResponse;
import com.example.recipeapp.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.recipeapp.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";

    public static RecipeApiClient instance;

    private MutableLiveData<List<Recipe>> recipes;
    private MutableLiveData<Recipe> recipe;
    private MutableLiveData<Boolean> isRecipeRequestTimeout;

    private RetrieveRecipesRunnable retrieveRecipesRunnable;
    private RetrieveRecipeRunnable retrieveRecipeRunnable;

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public RecipeApiClient() {
        recipes = new MutableLiveData<>();
        recipe = new MutableLiveData<>();
        isRecipeRequestTimeout = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public LiveData<Boolean> isRecipeRequestTimeout(){
        return isRecipeRequestTimeout;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (retrieveRecipesRunnable != null) {
            retrieveRecipesRunnable = null;
        }
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveRecipesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchRecipeApi(String recipeId) {
        if (retrieveRecipeRunnable != null) {
            retrieveRecipeRunnable = null;
        }
        retrieveRecipeRunnable = new RetrieveRecipeRunnable(recipeId);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveRecipeRunnable);

        isRecipeRequestTimeout.setValue(false);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                isRecipeRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if (pageNumber == 1) {
                        recipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = recipes.getValue();
                        currentRecipes.addAll(list);
                        recipes.postValue(currentRecipes);
                    }
                } else {
                    Log.e(TAG, "onResponse: errorBody" + response.errorBody().toString());
                    recipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipes.postValue(null);
            }
        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }

    private class RetrieveRecipeRunnable implements Runnable {
        private String recipeId;
        boolean cancelRequest;

        public RetrieveRecipeRunnable(String recipeId) {
            this.recipeId = recipeId;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipe(recipeId).execute();
                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    Recipe currentRecipe = ((RecipeResponse) response.body()).getRecipe();
                    recipe.postValue(currentRecipe);
                } else {
                    Log.e(TAG, "onResponse: errorBody" + response.errorBody().toString());
                    recipe.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipe.postValue(null);
            }
        }

        private Call<RecipeResponse> getRecipe(String recipeId) {
            return ServiceGenerator.getRecipeApi().getRecipe(
                    Constants.API_KEY,
                    recipeId
            );
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if (retrieveRecipesRunnable != null){
            retrieveRecipesRunnable.cancelRequest();
        }
        if (retrieveRecipeRunnable != null){
            retrieveRecipeRunnable.cancelRequest();
        }
    }
}

















