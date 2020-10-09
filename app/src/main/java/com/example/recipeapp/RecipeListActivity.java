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

        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void initRecyclerview(){
        recyclerView = findViewById(R.id.recipe_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setHasFixedSize(true);
        adapter = new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initSearchView(){
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

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
    }
}