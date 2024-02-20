package com.mygdx.game.Organism;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.WindowSingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;


public abstract class Organism {
    Sprite sprite;
    Texture texture;
    float speed = 100;




    protected ArrayList<Integer> makeAnIntervall(int position) {
        ArrayList<Integer> intervall = new ArrayList<>(Arrays.asList(position));

        int intervallLength = 2;
        for (int i = 1; i <= intervallLength; i++) {
            intervall.add(position + i);
            intervall.add(position - i);
        }
        return intervall;
    }



    //================= GETTERS AND SETTERS, CONSTRUCTOR =======================
    public Sprite getSprite()
    {return sprite;}


    public Texture getTexture()
    {return texture;}


    //============== METHODS =====================
    protected void createOrganism(String imagePath, float positionX, float positionY)
    {
        texture = new Texture(imagePath);
        sprite = new Sprite(texture);

        int blockSize = 50;
        sprite.setBounds(blockSize, blockSize, blockSize, blockSize);
        sprite.setPosition(positionX, positionY);
    }




    //================ ABSTRACT METHODS =================
    public abstract void handleMovement();



    public void drawOrganism()
    {
        sprite.draw(WindowSingleton.getInstance().getBatch());
    }


}
