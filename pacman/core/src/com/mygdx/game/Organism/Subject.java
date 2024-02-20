package com.mygdx.game.Organism;

public interface Subject {

    void registerObserver(PacmanObserver pacmanObserver);

    void removeObserver(PacmanObserver pacmanObserver);






    void notifyObserversPacmanIsPowered();

    void notifyObserversPacmanIsUnpowered();



}
