package com.lantsovapps.avoocadoo.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lantsovapps.avoocadoo.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    ImageView foodImage;
    TextView foodName, foodLikes;
    CardView cardView;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);

        foodImage = itemView.findViewById(R.id.foodImage);
        foodName = itemView.findViewById(R.id.foodName);
        foodLikes = itemView.findViewById(R.id.foodLikes);
        cardView = itemView.findViewById(R.id.cardView);

        foodName.setSelected(true);
    }


}
