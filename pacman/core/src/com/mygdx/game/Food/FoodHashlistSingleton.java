package com.mygdx.game.Food;

import java.util.List;

public class FoodHashlistSingleton {




    private FoodHashlistSingleton() {}
    static FoodHashlistSingleton instance;
    public static FoodHashlistSingleton getInstance() {
        if (instance == null)
            instance = new FoodHashlistSingleton();
        return instance;
    }



    static int hashLenght = (1500+1);
    private static Food foods[] = new Food[hashLenght];




    public static Food getFoodByPacmanPos(float posX, float posY) {
        int positionToSearchX = (int) (posX + FoodUtils.getFoodPushedFromCenterX());
        int positionToSearchY = (int) (posY + FoodUtils.getFoodPushedFromCenterY());

        int index = findIndexForFood(positionToSearchX, positionToSearchY);
        if (index == -1)
            return null;
        return foods[index];
    }


    public static int findIndexForFood(float posX, float posY) {
        int hashValue = makeHashvalue(posX, posY);
        int i = 0;
        while (true) {
            if ((hashValue + i) == hashLenght-1) { //hvis man er på slutten av hashlista, lopper man fra starten av lista istedenfor
                i = 0;
                hashValue = 0;
            }
            if (foods[hashValue + i] == null)
                return -1;
            if (foods[hashValue + i].posX == posX && foods[hashValue + i].posY == posY)
                return hashValue + i;
            i += 1;
        }
    }



    private static int makeHashvalue(float posX, float posY) {
        //Im using "middle of quadrant" hashing

        //adding a number depending on posX and posY to differentiate them
        String posXalter = String.valueOf( (int) posX ) + "3";
        String posYalter = String.valueOf( (int) posY) + "5";

        //combining positions
        String pos = posXalter + posYalter;

        //kvadrerer
        pos = String.valueOf( Integer.parseInt(pos) * Integer.parseInt(pos) );

        //getting a quadrant
        long quadrant = Integer.parseInt(pos.substring(3,pos.length()-3));

        //System.out.println(quadrant%hashLenght);
        return (int) (quadrant%hashLenght);
    }







    public static void addFood(Food food) {
        int hashValue = makeHashvalue(food.getPosX(), food.getPosY());
        int i = 0;
        while (true) {
            if ((hashValue + i) == hashLenght-1) { //hvis man er på slutten av hashlista, lopper man fra starten av lista istedenfor
                i = 0;
                hashValue = 0;
            }
            if (foods[hashValue + i] == null) {
                foods[hashValue + i] = food;
                break;
            }
            i += 1;
        }
    }

    public void setFoodAsEaten(Food food) {
        int index = findIndexForFood(food.getPosX(), food.getPosY());
        foods[index].setEaten(true);
    }

    public static void addAllFoods(List<Food> foods) {
        for (Food food : foods)
            addFood(food);
    }





}
