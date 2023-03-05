package com.example.recipeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String title;
    private String publisher;
    private String[] ingredients;
    private String pk;
    private String featured_image;
    private float rating;

    public Recipe(String title, String publisher, String[] ingredients, String recipeId, String imageUrl, float socialRank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.pk = recipeId;
        this.featured_image = imageUrl;
        this.rating = socialRank;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        pk = in.readString();
        featured_image = in.readString();
        rating = in.readFloat();
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
        return pk;
    }

    public void setRecipeId(String recipe_id) {
        this.pk = recipe_id;
    }

    public String getImageUrl() {
        return featured_image;
    }

    public void setImageUrl(String image_url) {
        this.featured_image = image_url;
    }

    public float getSocialRank() {
        return rating;
    }

    public void setSocialRank(float social_rank) {
        this.rating = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipeId='" + pk + '\'' +
                ", imageUrl='" + featured_image + '\'' +
                ", socialRank=" + rating +
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
        parcel.writeString(pk);
        parcel.writeString(featured_image);
        parcel.writeFloat(rating);
    }
}
