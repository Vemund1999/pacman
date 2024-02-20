package com.mygdx.game.Food;

public class FoodFactory {



    public static Orange createOrange(float posX, float posY) {
        return new Orange(posX, posY);
    }

    public static Cherry createCherry(float posX, float posY) {
        return new Cherry(posX, posY);
    }

    public static Cherry createCherry() {
        return new Cherry();
    }

    public static Orange createOrange() {
        return new Orange();
    }







}
