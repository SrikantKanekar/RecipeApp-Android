package com.example.recipeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String title;
    private String publisher;
    private String[] ingredients;
    private String recipe_id;
    private String image_url;
    private float social_rank;

    public Recipe(String title, String publisher, String[] ingredients, String recipeId, String imageUrl, float socialRank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.recipe_id = recipeId;
        this.image_url = imageUrl;
        this.social_rank = socialRank;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        recipe_id = in.readString();
        image_url = in.readString();
        social_rank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public float getSocialRank() {
        return social_rank;
    }

    public void setSocialRank(float social_rank) {
        this.social_rank = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipeId='" + recipe_id + '\'' +
                ", imageUrl='" + image_url + '\'' +
                ", socialRank=" + social_rank +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(publisher);
        parcel.writeStringArray(ingredients);
        parcel.writeString(recipe_id);
        parcel.writeString(image_url);
        parcel.writeFloat(social_rank);
    }
}
