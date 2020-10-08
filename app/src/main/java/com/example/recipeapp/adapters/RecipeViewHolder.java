package com.example.recipeapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title, publisher, socialScore;
    AppCompatImageView image;
    OnRecipeListener onRecipeListener;

    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        image = itemView.findViewById(R.id.recipe_image);
        title = itemView.findViewById(R.id.recipe_title);
        publisher = itemView.findViewById(R.id.recipe_publisher);
        socialScore = itemView.findViewById(R.id.recipe_social_score);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
    }
}
