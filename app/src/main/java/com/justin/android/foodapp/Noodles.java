package com.justin.android.foodapp;

public class Noodles {
    private String name, description;
    private int imageResourceId, price;
    private Extras extras;
    // private boolean isSoba;
    // private boolean hasMeat;
    /**
    public static final Noodles[] noodles = {

        // Meat noodles
        new Noodles("Duck Noodles", "Handmade soba noodles with sliced duck fillets", R.drawable.duck_soba, 20),
        new Noodles("Duck & Mushroom Noodles", "Handmade soba noodles with sliced duck fillets and Japanese Mushrooms", R.drawable.duck_and_mushroom_soba,23),
        new Noodles("Chicken & Mushroom Noodles", "Handmade soba noodles with chicken and japanese mushrooms", R.drawable.chicken_and_mushroom, 20),
        new Noodles("Mushroom Noodles", "Handmade soba noodles with assorted japanese mushrooms", R.drawable.mushroom_soba, 20),
        new Noodles("Curry Noodles", "Handmade soba noodles with chicken and japanese curry flavoured soup", R.drawable.curry_soba, 19),
        new Noodles("Walnut Soba Noodles", "Handmade soba noodles with chicken, vegetables and Japanese mushrooms in a walnut and sesame soup", R.drawable.walnut_soba, 20),
        new Noodles("Nabeyaki Udon Noodles", "Udon noodles in a hot pot soup with chicken, boiled vegetables, runny egg & prawn tempura", R.drawable.nabeyaki_udon, 22),

        // Vege Noodles
        new Noodles("Plain Noodles", "Plain handmade soba noodles", R.drawable.plain_soba, 13),
        new Noodles("Tempura Noodles", "Handmade soba noodles with assorted tempura", R.drawable.tempura_soba, 20),
        new Noodles("Oroshi Noodles", "Handmade soba noodles with mountain grated daikon radish", R.drawable.oroshi_soba, 18),
        new Noodles("Age-mochi Noodles", "Handmade soba noodles with fried sticky rice(mochi)", R.drawable.mochi_soba, 18),
        new Noodles("Kitsune Noodles", "Handmade soba noodles with marinated tasty bean curd", R.drawable.kitsune_soba, 18),
        new Noodles("Vege Lover's Soba Noodles", "Handmade soba noodles with assorted vegetable tempura and boiled vegetables", R.drawable.vege_soba, 19),
    };
     **/

    private Noodles(String name, String description, int imageResourceId, int price) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getPrice() {
        return price;
    }

    public Extras getExtras() {
        return extras;
    }
}
