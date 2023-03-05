package com.example.recipeapp.requests;

import com.example.recipeapp.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                    new OkHttpClient.Builder()
                            .addInterceptor(
                                    chain -> {
                                        Request request = chain.request().newBuilder().addHeader("Authorization", "Token 9c8b06d329136da358c2d00e76946b0111ce2c48").build();
                                        return chain.proceed(request);
                                    }
                            )
                            .build()
            );

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RecipeApi recipeApi = retrofit.create(RecipeApi.class);

    public static RecipeApi getRecipeApi() {
        return recipeApi;
    }

}
