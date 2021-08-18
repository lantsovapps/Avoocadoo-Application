package com.lantsovapps.avoocadoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lantsovapps.avoocadoo.Adapter.IngredientAdapter;
import com.lantsovapps.avoocadoo.Model.Ingredient;
import com.lantsovapps.avoocadoo.Model.Recipe;
import com.lantsovapps.avoocadoo.Model.StepsHolder;
import com.lantsovapps.avoocadoo.Retrofit.IMyAPI;
import com.lantsovapps.avoocadoo.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.lantsovapps.avoocadoo.MainActivity.API_KEY;
import static com.lantsovapps.avoocadoo.MainActivity.allRecipes;

public class RecipeDetails extends AppCompatActivity {

    private Intent intent;
    private ImageView imgRecipeDetails;
    private TextView tvDtlsName, tvDtlsIngredient, tvSteps;
    private Recipe currentRecipe;
    private IMyAPI iMyAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Ingredient> ingredients;
    private RecyclerView recyclerView_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        //views
        imgRecipeDetails = findViewById(R.id.imgRecipeDetails);
        tvDtlsName = findViewById(R.id.tvDtlsName);
        tvDtlsIngredient = findViewById(R.id.tvDtlsIngredient);
        tvSteps = findViewById(R.id.tvSteps);
        recyclerView_ingredients = findViewById(R.id.RVingredients);
        recyclerView_ingredients.setHasFixedSize(true);
        recyclerView_ingredients.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        Retrofit retrofit = RetrofitClient.getClient();
        iMyAPI = retrofit.create(IMyAPI.class);

        ingredients = new ArrayList<>();

        fetchData();

    }

    private void fetchData(){

        intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        currentRecipe = allRecipes.get(position);

        Picasso.with(getBaseContext()).load(currentRecipe.getImage()).into(imgRecipeDetails);
        tvDtlsName.setText(currentRecipe.getTitle());
        StringBuilder txt = new StringBuilder();



        for(Ingredient i: currentRecipe.getMissedIngredients()){
            txt = txt.append(i.getOriginal()).append("\n");
            ingredients.add(i);// add 26/07/2021
        }
        ingredients.add(currentRecipe.getUsedIngredients()[0]); // add 26/07/2021
//        tvDtlsIngredient.setText(txt.append(currentRecipe.getUsedIngredients()[0]));


        IngredientAdapter adapter = new IngredientAdapter(ingredients, this);
        recyclerView_ingredients.setAdapter(adapter);

        retrieveJson(currentRecipe, API_KEY);
    }

    private void retrieveJson(Recipe recipe, String apiKey){
        int id = recipe.getId();
        System.out.println(">>> Recipe ID: " + id);
        compositeDisposable.add(iMyAPI.getSteps(id, apiKey)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(steps -> displaySteps(steps)));
    }

    private void displaySteps(List<StepsHolder> steps){
        StringBuilder text = new StringBuilder();
        System.out.println(">>> StepsHolder List length: " + steps.size());
        for (StepsHolder step: steps){
            System.out.println(">>> Number of Steps:" + step.getSteps().length);
            for(int i=0; i < step.getSteps().length; i++) {
                text.append("\n").append(step.getSteps()[i].getNumber())
                        .append(". ").append(step.getSteps()[i].getStep());
            }
        }
        tvSteps.setText(text);
    }


}
