package com.example.recipeapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.repository.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private String recipeId;
    private boolean didRetrieveRecipe;

    public RecipeViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimeout(){
        return recipeRepository.isRecipeRequestTimeout();
    }

    public void searchRecipeApi(String recipeId){
        this.recipeId = recipeId;
        recipeRepository.searchRecipeApi(recipeId);
    }

    public String getRecipeId() {
        return recipeId;
    }

    public boolean DidRetrieveRecipe() {
        return didRetrieveRecipe;
    }

    public void setDidRetrieveRecipe(boolean didRetrieveRecipe) {
        this.didRetrieveRecipe = didRetrieveRecipe;
    }
}
