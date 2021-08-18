package com.lantsovapps.avoocadoo.Model;

public class Ingredient {
    private int id;
    private String original;
    private String image;

    public Ingredient() {
    }

    public Ingredient(int id, String original, String image) {
        this.id = id;
        this.original = original;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getOriginal() {
        return original;
    }


}
