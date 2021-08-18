package com.lantsovapps.avoocadoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.lantsovapps.avoocadoo.Adapter.RecipeAdapter;
import com.lantsovapps.avoocadoo.Model.Recipe;
import com.lantsovapps.avoocadoo.Retrofit.IMyAPI;
import com.lantsovapps.avoocadoo.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private IMyAPI iMyAPI;
    private RecyclerView recyclerView_recipes;
    private Button btnFilterLikes, btnFilterIngredients, buttonSearch;
    private SearchView searchView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static List<Recipe> allRecipes = new ArrayList<>();
    public static List<Recipe> allSortedRecipes = new ArrayList<>();
    private boolean sortIncrease = false;

    public static final String API_KEY = "YOUR KEY";
    private static final String FOOD = "avocado";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView_recipes = findViewById(R.id.recyclerView);
        recyclerView_recipes.setHasFixedSize(true);
        recyclerView_recipes.setLayoutManager(new LinearLayoutManager(this));
        btnFilterLikes = findViewById(R.id.btnFilterLikes);
        btnFilterIngredients = findViewById(R.id.btnFilterIngredients);
        searchView = findViewById(R.id.SearchView);


        Retrofit retrofit = RetrofitClient.getClient();
        iMyAPI = retrofit.create(IMyAPI.class);

        if(isConnectedToInternet(this)){
            retrieveJson(API_KEY, FOOD, 8);
            setButtonsListeners();
            //setSearch();
        } else {
            Toast.makeText(this, "Internet connection required", Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonsListeners() {
        btnFilterLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedListLikes();
            }
        });
        btnFilterIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedListRecipes();
            }
        });
    }

    private void retrieveJson(String apiKey, String ingredients, int number){
        compositeDisposable.add(iMyAPI.getRecipes(apiKey, ingredients, number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(recipes -> displayData(recipes)));
    }

    private void displayData(List<Recipe> recipes){
        System.out.println(">>> start displayData");
        allRecipes.addAll(recipes);
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes, this);
        recyclerView_recipes.setAdapter(recipeAdapter);
        System.out.println(">>> Recipes array size: " + allRecipes.size());
    }


    private void sortedListLikes(){
        System.out.println(">>> Start sorting by Likes");
        if(!sortIncrease) {
            allSortedRecipes.clear();
            Observable<Recipe> listObservable = Observable.fromIterable(allRecipes);
            compositeDisposable.add(listObservable
                    .subscribeOn(Schedulers.io())
                    .sorted((x, y) -> Integer.compare(y.getLikes(), x.getLikes()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(recipe -> allSortedRecipes.add(recipe)
                            , e -> e.printStackTrace()
                            , () -> displaySortedData()));
            sortIncrease = true;
        } else {
            allSortedRecipes.clear();
            Observable<Recipe> listObservable = Observable.fromIterable(allRecipes);
            compositeDisposable.add(listObservable
                    .subscribeOn(Schedulers.io())
                    .sorted((x, y) -> Integer.compare(x.getLikes(), y.getLikes()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(recipe -> allSortedRecipes.add(recipe)
                            , e -> e.printStackTrace()
                            , () -> displaySortedData()));
            sortIncrease = false;
        }
    }

    private void sortedListRecipes(){
        System.out.println(">>> Start sorting by Recipes");
        if(!sortIncrease) {
            allSortedRecipes.clear();
            Observable<Recipe> listObservable = Observable.fromIterable(allRecipes);
            compositeDisposable.add(listObservable
                    .subscribeOn(Schedulers.io())
                    .sorted((x, y) -> Integer.compare(y.getMissedIngredients().length, x.getMissedIngredients().length))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(recipe -> allSortedRecipes.add(recipe)
                            , e -> e.printStackTrace()
                            , () -> displaySortedData()));
            sortIncrease = true;
        } else {
            allSortedRecipes.clear();
            Observable<Recipe> listObservable = Observable.fromIterable(allRecipes);
            compositeDisposable.add(listObservable
                    .subscribeOn(Schedulers.io())
                    .sorted((x, y) -> Integer.compare(x.getMissedIngredients().length, y.getMissedIngredients().length))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(recipe -> allSortedRecipes.add(recipe)
                            , e -> e.printStackTrace()
                            , () -> displaySortedData()));
            sortIncrease = false;
        }
    }

    private void displaySortedData(){
        System.out.println(">>> start displaySortedData");
        RecipeAdapter recipeAdapter = new RecipeAdapter(allSortedRecipes, this);
        recyclerView_recipes.setAdapter(recipeAdapter);
        System.out.println(">>> Sorted recipes array size: " + allSortedRecipes.size());
    }


    private void setSearch(){
        //Prepare Observable
        System.out.println(">>> Start search observable");
        Observable<String> observable = Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                if(!emitter.isDisposed()){
                                    emitter.onNext(newText); // Pass the query to the emitter
                                }
                                return false;
                            }
                        });
                    }
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //Subscribe to Observable
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {
                if(!s.equals("")) {
                    setSearchedList(s);
                } else {
                    RecipeAdapter recipeAdapter = new RecipeAdapter(allRecipes, MainActivity.this);
                    recyclerView_recipes.setAdapter(recipeAdapter);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void setSearchedList(String s){
        allSortedRecipes.clear();

        System.out.println(">>> set Searched List");
        for (Recipe r: allRecipes){
            String r_name = r.getTitle().toLowerCase();
            if(r_name.contains(s.toLowerCase())){
                allSortedRecipes.add(r);
                RecipeAdapter recipeAdapter = new RecipeAdapter(allSortedRecipes, this);
                recyclerView_recipes.setAdapter(recipeAdapter);

            }
        }
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null){
                for(int i=0; i<info.length; i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if(isConnectedToInternet(this)){
            System.out.println(">>>RESUMED");
            setSearch();
        }
        super.onResume();
    }
}
