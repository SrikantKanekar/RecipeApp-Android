package com.example.recipeapp.util;

import android.util.Log;

import com.example.recipeapp.models.Recipe;

import java.util.List;

public class Testing {
    public static void printRecipes(List<Recipe> list, String tag){
        for (Recipe recipe: list){
            Log.e(tag, "title " + recipe.getTitle() + " publisher " + recipe.getPublisher() + " rank " + recipe.getSocialRank());
        }
    }
}
