package com.mygdx.game.Organism;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Food.Cherry;
import com.mygdx.game.Food.Food;
import com.mygdx.game.Food.FoodHashlistSingleton;
import com.mygdx.game.Food.FoodUtils;
import com.mygdx.game.NodeTree.Node;
import com.mygdx.game.NodeTree.NodeTreeSingelton;

import java.util.*;

public class Pacman extends Organism implements InputProcessor, Subject {

    //============================ SUBJECT ================================
    List<PacmanObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(PacmanObserver pacmanObserver) {
        observers.add(pacmanObserver);
    }

    @Override
    public void removeObserver(PacmanObserver pacmanObserver) {
        observers.remove(pacmanObserver);
    }


    @Override
    public void notifyObserversPacmanIsPowered() {
        for (PacmanObserver observer : observers)
            observer.updatePacmanIsPowered();
    }

    @Override
    public void notifyObserversPacmanIsUnpowered() {
        for (PacmanObserver observer : observers)
            observer.updatePacmanIsUnpowered();
    }






    //============================ INSTANTIATION ================================


    private static Pacman instance;
    public static Pacman getInstance()
    {
        if (instance == null)
            instance = new Pacman();
        return instance;
    }

    private Pacman()
    {
        createOrganism("pacman.png", 740, 550);
    }








    //============================ MOVMENT ================================


    public boolean collidesWith(Sprite otherSprite) {
        return sprite.getBoundingRectangle().overlaps(otherSprite.getBoundingRectangle());
    }


    private void eatFoodIfSteppdOn(List<Integer> xPosIntervall, List<Integer> yPosIntervall) {
        int xPosToSearch = 0;
        int yPosToSearch = 0;
        for (int i = 0; i < xPosIntervall.size(); i++) {
            if (xPosIntervall.get(i) % 5 == 0) {
                xPosToSearch = xPosIntervall.get(i);
                break;
            }
        }
        for (int i = 0; i < yPosIntervall.size(); i++) {
            if (yPosIntervall.get(i) % 5 == 0) {
                yPosToSearch = yPosIntervall.get(i);
                break;
            }
        }
        Food food = FoodHashlistSingleton.getInstance().getFoodByPacmanPos(xPosToSearch, yPosToSearch);
        if (food != null && !food.isEaten)
            FoodUtils.eatFood(food);

        if (food instanceof Cherry)
            powerUpPacman();
    }


    public void powerUpPacman() {
        notifyObserversPacmanIsPowered();

        long delay = 10000L;  //10 sec
        Timer timer = new Timer("Timer");
        TimerTask unpowerPacman = new TimerTask() {
            @Override
            public void run() {
                notifyObserversPacmanIsUnpowered();
            }
        };
        timer.schedule(unpowerPacman, delay);

    }






    int direction = 0;
    int nextDirection = 0;
    Node fromNode = NodeTreeSingelton.getInstance().getRootNode();
    Node toNode = null;

    int none = 0;
    int up = 1;
    int down = -1;
    int right = 2;
    int left = -2;
    @Override
    public void handleMovement() {

        Gdx.input.setInputProcessor(this);

        float moveAmount = speed * Gdx.graphics.getDeltaTime();

        setNextDirectionToMoveIn();

        ArrayList<Integer> xPosIntervall = makeAnIntervall((int) sprite.getX());
        ArrayList<Integer> yPosIntervall = makeAnIntervall((int) sprite.getY());



        if (xPosIntervall.contains((int)fromNode.getPosX()) && yPosIntervall.contains((int)fromNode.getPosY()))
            setDirectionToMoveIfStandingOnFromNode();



        if (toNode != null)
            if (xPosIntervall.contains((int)toNode.getPosX()) && yPosIntervall.contains((int)toNode.getPosY()))
                setDirectionToMoveIfStandingOnToNode();

        eatFoodIfSteppdOn(xPosIntervall, yPosIntervall);
        moveInDirection(moveAmount);
        NodeTreeSingelton.setStartNodeandEndNodeToMark(new ArrayList<>(Arrays.asList(fromNode, toNode)));

    }





    private void moveInDirection(float moveAmount) {
        float xPosition = sprite.getX();
        float yPosition = sprite.getY();


        if (direction == none)
            sprite.setPosition(xPosition, yPosition);
        else if (direction == up) {

            yPosition += moveAmount;

        }
        else if (direction == down) {
            yPosition -= moveAmount;
        }
        else if (direction == left) {
            xPosition -= moveAmount;
            sprite.setFlip(true, false);
        }
        else if (direction == right) {
            xPosition += moveAmount;
            sprite.setFlip(false, false);

        }
        sprite.setPosition(xPosition,yPosition);
    }

    private void setDirectionToMoveIfStandingOnToNode() {
        if (nextDirection == 1) {
            fromNode = toNode;
            toNode = toNode.getUp();
            direction = 1;
        }
        else if (nextDirection == -1) {
            fromNode = toNode;
            toNode = toNode.getDown();
            direction = -1;
        }
        else if (nextDirection == 2) {
            fromNode = toNode;
            toNode = toNode.getRight();
            direction = 2;
        }
        else if (nextDirection == -2) {
            fromNode = toNode;
            toNode = toNode.getLeft();
            direction = -2;
        }


        if (toNode == null)
            direction = 0;
    }

    private void setDirectionToMoveIfStandingOnFromNode() {
        if (nextDirection == 1)
        {
            toNode = fromNode.getUp();
            direction = 1;
        }
        else if (nextDirection == -1)
        {
            toNode = fromNode.getDown();
            direction = -1;
        }
        else if (nextDirection == 2)
        {
            toNode = fromNode.getRight();
            direction = 2;
        }
        else if (nextDirection == -2)
        {
            toNode = fromNode.getLeft();
            direction = -2;
        }


        if (toNode == null)
            direction = 0;
    }




    private void setNextDirectionToMoveIn() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            nextDirection = down;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            nextDirection = up;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            nextDirection = left;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            nextDirection = right;
        if (nextDirection == direction*-1)
            direction = nextDirection;
    }


    //============================ KEYS ================================



    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }



}
