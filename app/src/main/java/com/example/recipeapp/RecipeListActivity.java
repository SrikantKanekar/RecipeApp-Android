package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.adapters.OnRecipeListener;
import com.example.recipeapp.adapters.RecipeRecyclerAdapter;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.util.Testing;
import com.example.recipeapp.util.VerticalSpacingItemDecorator;
import com.example.recipeapp.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private RecipeListViewModel recipeListViewModel;
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        initRecyclerview();
        initSearchView();
        subscribeObservers();

        if (!recipeListViewModel.isViewingRecipes()){
            displayCategories();
        }
        
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void subscribeObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null){
                    if (recipeListViewModel.isViewingRecipes()){
                        recipeListViewModel.setPerformingQuery(false);
                        adapter.setRecipes(recipes);
                    }
                }
            }
        });

        recipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    adapter.setQueryExhausted();
                }
            }
        });
    }

    private void initRecyclerview(){
        recyclerView = findViewById(R.id.recipe_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setHasFixedSize(true);
        adapter = new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    recipeListViewModel.searchNextPage();
                }
            }
        });
    }

    private void initSearchView(){
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.displayLoading();
                recipeListViewModel.searchRecipesApi(s, 1);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", adapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        adapter.displayLoading();
        recipeListViewModel.searchRecipesApi(category, 1);
        searchView.clearFocus();
    }

    private void displayCategories(){
        recipeListViewModel.setViewingRecipes(false);
        adapter.displayCategories();
    }

    @Override
    public void onBackPressed() {
        if (recipeListViewModel.onBackPressed()){
            super.onBackPressed();
        } else {
            displayCategories();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_categories){
            displayCategories();
        }
        return super.onOptionsItemSelected(item);
    }
}