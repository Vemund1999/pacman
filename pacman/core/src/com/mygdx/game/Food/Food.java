package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.WindowSingleton;

public abstract class Food {
    Sprite sprite;
    Texture texture;

    float posX; float posY;


    private int index;

    public boolean isEaten = false;

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    protected void createOrganism(String imagePath, float positionX, float positionY) {
        texture = new Texture(imagePath);
        sprite = new Sprite(texture);

        int blockSize = 10;
        sprite.setBounds(blockSize, blockSize, blockSize, blockSize);
        sprite.setPosition(positionX, positionY);
    }

    protected void createOrganism(String imagePath) {
        texture = new Texture(imagePath);
        sprite = new Sprite(texture);

        int blockSize;
        if (this instanceof Orange)
            blockSize = 7;
        else
            blockSize = 15;

        sprite.setBounds(blockSize, blockSize, blockSize, blockSize);
    }

    protected void setBounds(float posX, float posY) {
        sprite.setPosition(posX, posY);
    }





    public void drawFood() {
        sprite.draw(WindowSingleton.getInstance().getBatch());
    }






    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }





}
