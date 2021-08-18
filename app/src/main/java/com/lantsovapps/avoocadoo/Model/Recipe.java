package com.lantsovapps.avoocadoo.Model;

import android.content.Intent;

public class Recipe {

    private int id;
    private String title;
    private String image;
    private Ingredient[] missedIngredients;
    private Ingredient [] usedIngredients;
    private int likes;

    public Recipe() {
    }

    public Recipe(int id, String title, String image, Ingredient[] missedIngredients, Ingredient[] usedIngredients, int likes) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.missedIngredients = missedIngredients;
        this.usedIngredients = usedIngredients;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ingredient[] getMissedIngredients() {
        return missedIngredients;
    }

    public void setMissedIngredients(Ingredient[] missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public Ingredient[] getUsedIngredients() {
        return usedIngredients;
    }

    public void setUsedIngredients(Ingredient[] usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
