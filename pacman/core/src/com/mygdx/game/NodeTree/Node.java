package com.mygdx.game.NodeTree;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.WindowSingleton;

import java.util.*;

public class Node {

    private float posX, posY;
    private Node left, right, up, down;
    private int wleft, wright, wup, wdown;

    //liste av naboene: <nabo, vekt mot nabo>
    private Map<Node, Integer> neighbours = new HashMap<>();






    //standard farge
    private final Texture texture = new Texture("red.png");
    private final Sprite sprite = new Sprite(texture);

    //når markert
    private final Texture markedTexture = new Texture("blue.png");
    private final Sprite markedSprite = new Sprite(markedTexture);


    private final Texture markedStart = new Texture("rosa.png");
    private final Sprite markedStartSprite = new Sprite(markedStart);


    private final Texture markedEnd= new Texture("oransj.png");
    private final Sprite markedEndSprite = new Sprite(markedEnd);



    public Node(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        sprite.setPosition(this.posX, this.posY);
        sprite.setSize(10, 10);



        markedSprite.setPosition(this.posX, this.posY);
        markedSprite.setSize(10, 10);

        markedStartSprite.setPosition(this.posX, this.posY);
        markedStartSprite.setSize(10, 10);

        markedEndSprite.setPosition(this.posX, this.posY);
        markedEndSprite.setSize(10, 10);
    }






    public void markAsStartnode()
    {
        markedStartSprite.draw(WindowSingleton.getInstance().getBatch());
    }

    public void markAsEndnode()
    {
        markedEndSprite.draw(WindowSingleton.getInstance().getBatch());
    }


    public void draw()
    {
        sprite.draw(WindowSingleton.getInstance().getBatch());
    }

    public void mark()
    {
        markedSprite.draw(WindowSingleton.getInstance().getBatch());

    }




    private void addNeighbours(Node node, int weight)
    {
        neighbours.put(node, weight);
        node.neighbours.put(this, weight);

    }


    public void setThisUpAgainstDown(Node node, int weight)
    {
        node.up = this;
        this.down = node;

        node.wup = weight;
        this.wdown = weight;

        addNeighbours(node, weight);
    }


    public void setThisLeftAgainstRight(Node node, int weight)
    {
        node.left = this;
        this.right = node;

        node.wleft = weight;
        this.wright = weight;

        addNeighbours(node, weight);

    }



    public Map<Node, Integer> getNeighbours() {
        return neighbours;
    }

    public Sprite getSprite()
    {
        return sprite;
    }


    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }



    public Node getUp() {
        return up;
    }

    public Node getDown() {
        return down;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }



    public int getWleft() {
        return wleft;
    }

    public int getWright() {
        return wright;
    }

    public int getWup() {
        return wup;
    }

    public int getWdown() {
        return wdown;
    }

}
