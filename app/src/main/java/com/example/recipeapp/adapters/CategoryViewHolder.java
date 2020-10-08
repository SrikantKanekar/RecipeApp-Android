package com.example.recipeapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    CircleImageView circleImageView;
    TextView categoryTitle;
    OnRecipeListener recipeListener;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener recipeListener) {
        super(itemView);
        this.recipeListener = recipeListener;
        circleImageView = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        recipeListener.onCategoryClick(categoryTitle.getText().toString());
    }
}
