package com.mygdx.game.Food;

import com.mygdx.game.NodeTree.Node;
import com.mygdx.game.NodeTree.NodeTreeSingelton;
import com.mygdx.game.WindowSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodUtils {
    private static List<Food> foods = new ArrayList<>();

    private final static float foodPushedFromCenterX = 20;
    private final static float foodPushedFromCenterY = 20;


    private static int foodSize = 0;

    private static void increaseFoodSize() {
        foodSize+=1;
    }

    public static int getFoodSize() {
        return foodSize;
    }

    private static void decreaseFoodSize() {
        foodSize-=1;
    }


    public static void drawFoods() {
        for (Food food : foods)
            if (food != null)
                food.drawFood();
    }



    public static void removeFood(int index) {
        foods.set(index, null);
        decreaseFoodSize();
    }

    public static void eatFood(Food food) {
        FoodUtils.removeFood(food.getIndex());
        FoodHashlistSingleton.getInstance().setFoodAsEaten(food);
        if (foodSize == 0)
            WindowSingleton.getInstance().stopGame(); //pacman har spist opp all maten, og har derfor vunnet
    }

    public static void placeFoods() {
        List<Node> nodes = NodeTreeSingelton.getInstance().getAllNodesAsListed();
        List<Node> visitedNodes = new ArrayList<>();
        int foodDistance = 100; //jeg tror vekt 1 tilsvarer 100pix

        int k = 0; //index for food
        int i = 0; //index for node
        while (true) {
            if (visitedNodes.size() == nodes.size()) //hvis alle noder er besøkt, end loop
                break;

            Node node = nodes.get(i);
            i++;
            if (visitedNodes.contains(node)) //hvis noden er besøkt, gå til neste node istedenfor
                continue;

            Map<Node, Integer> neighbours = node.getNeighbours(); //get neighbours of node

            boolean foodPlacedOnNode = false; //if food has been placed on the node (not the neighbour)
            for (Map.Entry<Node, Integer> neighbour : neighbours.entrySet()) //loop alle naboer
            {
                if (!visitedNodes.contains(neighbour.getKey())) { //hvis naboen ikke er besøkt
                    int distanceToNeighbour = neighbour.getValue() * 100; //100 fordi 100 er størrelsen til en vekt
                    for (int a = 0; a < distanceToNeighbour-foodDistance; a+=foodDistance) //loop punkter mellom noden og naboen, på alle punktene utenom på nabonoden
                    {
                        if (foodPlacedOnNode && a == 0)
                            continue;



                        Food food;
                        float aestheticPushX;
                        float aestheticPushY;
                        //skal maten som plasseres være spesiell eller ikke
                        if ((node.getPosX() == 1500-100 || node.getPosX()==100) && (node.getPosY()==100 || node.getPosY()==675) && a==0) {
                            food = FoodFactory.createCherry();
                            aestheticPushX = foodPushedFromCenterX; //the food needs its position sligtly altered for aesthetics
                            aestheticPushY = foodPushedFromCenterY;
                        }
                        else {
                            food = FoodFactory.createOrange();
                            aestheticPushX = foodPushedFromCenterX;
                            aestheticPushY = foodPushedFromCenterY;
                        }



                        //hvor skal maten settes i forhold til noden? Over? til høyre? under? til venstre?
                        if (neighbour.getKey() == node.getUp()) {
                            food.setBounds(node.getPosX() + aestheticPushX, node.getPosY()+a + aestheticPushY);
                            food.setPosX(node.getPosX() + aestheticPushX);
                            food.setPosY(node.getPosY()+a + aestheticPushY);
                        }
                        else if (neighbour.getKey() == node.getRight()) {
                            food.setBounds(node.getPosX()+a + aestheticPushX, node.getPosY() + aestheticPushY);
                            food.setPosX(node.getPosX()+a + aestheticPushX);
                            food.setPosY(node.getPosY() + aestheticPushY);
                        }
                        else if (neighbour.getKey() == node.getDown()) {
                            food.setBounds(node.getPosX() + aestheticPushX, node.getPosY()-a + aestheticPushY);
                            food.setPosX(node.getPosX() + aestheticPushX);
                            food.setPosY(node.getPosY()-a + aestheticPushY);
                        }
                        else if (neighbour.getKey() == node.getLeft()) {
                            food.setBounds(node.getPosX()-a + aestheticPushX, node.getPosY() + aestheticPushY);
                            food.setPosX(node.getPosX()-a + aestheticPushX);
                            food.setPosY(node.getPosY() + aestheticPushY);
                        }

                        foodPlacedOnNode = true;

                        food.setIndex(k);
                        foods.add(food); //legg til maten i en liste over all maten
                        increaseFoodSize();
                        k+=1;
                    }
                }
            }

            visitedNodes.add(node); //marker noden som besøkt


        }

    }





    public static List<Food> getFoods() {
        return foods;
    }

    public static float getFoodPushedFromCenterX() {
        return foodPushedFromCenterX;
    }
    public static float getFoodPushedFromCenterY() {
        return foodPushedFromCenterY;
    }






}
