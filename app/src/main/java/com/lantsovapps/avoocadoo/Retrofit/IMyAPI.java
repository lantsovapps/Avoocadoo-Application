package com.lantsovapps.avoocadoo.Retrofit;

import com.lantsovapps.avoocadoo.Model.Recipe;
import com.lantsovapps.avoocadoo.Model.StepsHolder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMyAPI {

    //https://api.spoonacular.com/recipes/findByIngredients?apiKey=a1c8a709b54f480fb93f35996c4671c0&ingredients=avocado&number=4
    @GET("findByIngredients")
    Observable<List<Recipe>> getRecipes(
        @Query("apiKey") String apiKey,
        @Query("ingredients") String ingredients,
        @Query("number") int number
    );

    //https://api.spoonacular.com/recipes/633167/analyzedInstructions?apiKey=a1c8a709b54f480fb93f35996c4671c0
    @GET("{id}/analyzedInstructions")
    Observable<List<StepsHolder>> getSteps(
            @Path("id") int id,
            @Query("apiKey") String apiKey
    );
}
