package com.example.recipeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.viewmodels.RecipeViewModel;

public class RecipeActivity extends BaseActivity {
    private static final String TAG = "tag";
    private AppCompatImageView imageView;
    private TextView title, rank;
    private LinearLayout ingredientContainer;
    private ScrollView scrollView;

    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        showProgressBar(true);
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        imageView = findViewById(R.id.recipe_image);
        title = findViewById(R.id.recipe_title);
        rank = findViewById(R.id.recipe_social_score);
        ingredientContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);
        getIncomingIntent();
        subscribeObservers();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            recipeViewModel.searchRecipeApi(recipe.getRecipeId());
        }
    }

    private void subscribeObservers() {
        recipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    if (recipeViewModel.getRecipeId().equals(recipe.getRecipeId())) {
                        setRecipeProperties(recipe);
                        recipeViewModel.setDidRetrieveRecipe(true);
                    }
                }
            }
        });

        recipeViewModel.isRecipeRequestTimeout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !recipeViewModel.DidRetrieveRecipe()) {
                    displayErrorScreen("Error Retrieving Data. Check Network Connection");
                }
            }
        });
    }

    private void displayErrorScreen(String errorMessage) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_background)
                .into(imageView);

        title.setText("Error retrieving recipe");
        rank.setText("");

        ingredientContainer.removeAllViews();
        TextView textView = new TextView(this);
        if (errorMessage.equals("")) {
            textView.setText("Error");
        } else {
            textView.setText(errorMessage);
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ingredientContainer.addView(textView);
        showParent();
        showProgressBar(false);
        recipeViewModel.setDidRetrieveRecipe(true);
    }

    private void setRecipeProperties(Recipe recipe) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.getImageUrl())
                .into(imageView);

        title.setText(recipe.getTitle().toString());
        rank.setText(String.valueOf(Math.round(recipe.getSocialRank())));

        ingredientContainer.removeAllViews();
        for (String ingredient : recipe.getIngredients()) {
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            textView.setTextSize(15);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            ingredientContainer.addView(textView);
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }
}
