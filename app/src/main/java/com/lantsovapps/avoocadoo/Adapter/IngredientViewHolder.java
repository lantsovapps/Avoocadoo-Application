package com.lantsovapps.avoocadoo.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lantsovapps.avoocadoo.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    TextView tVIngredient;
    ImageView imgIngredient;


    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        tVIngredient = itemView.findViewById(R.id.tVIngredient);
        imgIngredient = itemView.findViewById(R.id.imgIngredient);
    }
}
