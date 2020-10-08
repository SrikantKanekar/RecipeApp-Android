package com.example.recipeapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient recipeApiClient;
    private String query;
    private int pageNumber;
    private MutableLiveData<Boolean> isQueryExhausted;
    private MediatorLiveData<List<Recipe>> recipes;

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {
        recipeApiClient = RecipeApiClient.getInstance();
        isQueryExhausted = new MutableLiveData<>();
        recipes = new MediatorLiveData<>();
        initMediatorLiveData();
    }

    private void initMediatorLiveData() {
        LiveData<List<Recipe>> recipeListApiSource = recipeApiClient.getRecipes();
        recipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipesList) {
                if (recipesList != null) {
                    recipes.setValue(recipesList);
                    doneQuery(recipesList);
                } else {
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<Recipe> list) {
        if (list != null) {
            if (list.size() % 30 != 0) {
                isQueryExhausted.setValue(true);
            }
        } else {
            isQueryExhausted.setValue(true);
        }
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public LiveData<Boolean> isQueryExhausted() {
        return isQueryExhausted;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        this.query = query;
        this.pageNumber = pageNumber;
        isQueryExhausted.setValue(false);
        recipeApiClient.searchRecipesApi(query, pageNumber);
    }

    public LiveData<Recipe> getRecipe() {
        return recipeApiClient.getRecipe();
    }

    public void searchRecipeApi(String recipeId) {
        recipeApiClient.searchRecipeApi(recipeId);
    }

    public LiveData<Boolean> isRecipeRequestTimeout() {
        return recipeApiClient.isRecipeRequestTimeout();
    }

    public void searchNextPage() {
        recipeApiClient.searchRecipesApi(query, pageNumber + 1);
    }

    public void cancelRequest() {
        recipeApiClient.cancelRequest();
    }
}
