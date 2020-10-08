package com.example.recipeapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private boolean isViewingRecipes;
    private boolean isPerformingQuery;

    public RecipeListViewModel(){
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getRecipes();
    }

    public LiveData<Boolean> isQueryExhausted() {
        return recipeRepository.isQueryExhausted();
    }

    public void searchRecipesApi(String query, int pageNumber){
        isViewingRecipes = true;
        isPerformingQuery = true;
        recipeRepository.searchRecipesApi(query, pageNumber);
    }

    public void searchNextPage(){
        if (!isPerformingQuery
                && isViewingRecipes
                && !recipeRepository.isQueryExhausted().getValue()){
            recipeRepository.searchNextPage();
        }
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }

    public boolean isPerformingQuery() {
        return isPerformingQuery;
    }

    public void setPerformingQuery(boolean performingQuery) {
        isPerformingQuery = performingQuery;
    }

    public boolean onBackPressed(){
        if (isPerformingQuery){
            recipeRepository.cancelRequest();
            isPerformingQuery = false;
        }
        if (isViewingRecipes){
            isViewingRecipes = false;
            return false;
        }
        return true;
    }
}
