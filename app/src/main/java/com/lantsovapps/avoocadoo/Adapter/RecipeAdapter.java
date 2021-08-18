package com.lantsovapps.avoocadoo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lantsovapps.avoocadoo.Model.Recipe;
import com.lantsovapps.avoocadoo.R;
import com.lantsovapps.avoocadoo.RecipeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe r = recipeList.get(position);
        String url = r.getImage();
        Picasso.with(context).load(url).into(holder.foodImage);

        holder.foodName.setText(r.getTitle());
        holder.foodLikes.setText(Integer.toString(r.getLikes()));

        //set OnClickListener and Intent to recipe details activity
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RecipeDetails.class);
                i.putExtra("position", position);
                System.out.println(">>> Click on position: " + position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
