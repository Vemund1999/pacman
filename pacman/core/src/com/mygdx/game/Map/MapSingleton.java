package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.WindowSingleton;
import java.util.ArrayList;



public class MapSingleton {



    private static MapSingleton instance;

    private Texture maptextture = new Texture("map.png");
    private Sprite map = new Sprite(maptextture);




    private MapSingleton() {}
    public static MapSingleton getInstance()
    {
        if (instance == null)
            instance = new MapSingleton();
        return instance;
    }





    public void createMap()
    {
        map.setBounds(0, 0,1500,800);
        map.setPosition(0, 0);
    }





    public void drawMap()
    {
        map.draw(WindowSingleton.getInstance().getBatch());
    }















}
