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
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
        }
    }

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }
}
