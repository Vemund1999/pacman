package com.mygdx.game.Food;

import com.mygdx.game.Food.Food;

public class Orange extends Food {

    public Orange(float posX, float posY) {
        createOrganism("appelsin.png", posX, posY);
    }

    public Orange() {
        createOrganism("appelsin.png");
    }
}
