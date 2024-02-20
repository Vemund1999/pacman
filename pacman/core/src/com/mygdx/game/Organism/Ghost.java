package com.mygdx.game.Organism;


import com.badlogic.gdx.Gdx;
import com.mygdx.game.Dijkstras.Dijkstras;
import com.mygdx.game.NodeTree.Node;
import com.mygdx.game.NodeTree.NodeTreeSingelton;
import com.mygdx.game.WindowSingleton;

import java.util.*;

public class Ghost extends Organism implements PacmanObserver {



    //============== PACMAN OBSERVER =========================
    private boolean runAway = false;


    @Override
    public void updatePacmanIsPowered() {
        runAway = true;


    }


    @Override
    public void updatePacmanIsUnpowered() {
        runAway = false;


    }





    //============== INSTANTIATION =========================


    Node startingNode = NodeTreeSingelton.getInstance().getRootNode();


    boolean alive = false;
    public static int ghostsInGrave = 0;

    int spawnPosX = 850;
    int spawnPosY = 370;

    boolean hibernatingInGrave = true;


    public void sleep(int ghostsInGrave) {
        TimerTask wakeUpFromHibernation = new TimerTask() {
            @Override
            public void run() {
                hibernatingInGrave = false;
            }
        };
        System.out.println("in sleep");

        long delay = 10000L *ghostsInGrave; //10 sec
        System.out.println("what");
        System.out.println(ghostsInGrave);
        System.out.println(delay);
        Timer timer = new Timer("Timer");
        timer.schedule(wakeUpFromHibernation, delay);
    }


    public boolean isHibernatingInGrave() {
        return hibernatingInGrave;
    }

    private static List<Boolean> takenGravePosition;

    //i starten kommer de i graven,
    // og bruker 10 sekk etter hverandre på å respawne

    //når de blir drept kommer de på det tidligst ledige posisjonen
    //de bruker 10 sekk på å respawne, uavhengig av hvor de står
    boolean spawnedForFirstTime;

    public Ghost()
    {
        this.spawnedForFirstTime = true;

        Pacman.getInstance().registerObserver(this);
        System.out.println("amount: " + ghostsInGrave);
        ghostsInGrave++; // There is a new ghost at the ghosts' spawn point
        System.out.println("amount: " + ghostsInGrave);
        spawnPosX = -(ghostsInGrave*50) + spawnPosX;
        createOrganism("ghost.png", spawnPosX, spawnPosY);
        sleep(ghostsInGrave); // The ghosts starts off sleeping at its spawn point before it starts to move
        System.out.println("done sleeping");
    }

    public int getSpawnPosX() {
        return spawnPosX;
    }

    public Ghost(boolean spawnedForFirstTime)
    {

        List<Integer> spawnPoses = new ArrayList<>();
        Pacman.getInstance().registerObserver(this);
        for (Ghost ghost : WindowSingleton.getInstance().getGhosts()) {
            spawnPoses.add( ghost.getSpawnPosX() );
        }

        for (int i = 0; i < 4; i++) {
            if ( !spawnPoses.contains(-(i*50) + spawnPosX) )
                spawnPosX = -(i*50) + spawnPosX;
        }
        createOrganism("ghost.png", spawnPosX, spawnPosY);

        sleep(1); // The ghosts starts off sleeping at its spawn point before it starts to move
    }



    //============== MOVMENT =========================


    Node currentNode = startingNode; //Node source
    Node toNode = null;
    Node toNodeHold = null;

    boolean moveToStart = false;

    float moveAmount;
    boolean nodeMovingToHit = false;
    int moveDirection = 0;

    @Override
    public void handleMovement() {

        float deltaTime = Gdx.graphics.getDeltaTime();
        moveAmount = speed * deltaTime;
        List<Node> path = null;

        float xPosition = sprite.getX();
        float yPosition = sprite.getY();
        ArrayList<Integer> xPositionIntervall = makeAnIntervall((int) xPosition);
        ArrayList<Integer> yPositionIntervall = makeAnIntervall((int) yPosition);


        if (!alive) // If the ghost is at the spawn point, it should move out of the spawn point. The code below is to get it to move in a particular way to get out of the spawn point.
            moveOutOfHibernation(moveAmount, xPositionIntervall, yPositionIntervall);

        else // if the ghost has come out of the spawn point, it should start moving based on pacman's position
        {

            if (runAway) { // if pacman has eaten a grape, then the ghost should run away
                path = makeRunAwayPath(xPositionIntervall, yPositionIntervall);
                dieIfPacmanStandsOnGhost(xPositionIntervall, yPositionIntervall);
                }


            else { // if pacman hasn't eaten a grape, then hunt pacman
                path = findPathHunt(xPositionIntervall, yPositionIntervall);
                stopGameIfGhostEatsPacman(xPositionIntervall, yPositionIntervall);
            }


            //markNodes(); // for visualizing the paths of the ghosts
            setToNodeFromPath(path); // set the node to move to, based on the path made for the way to move
            moveInDirection(); // move towards the node

        }

    }









    private void moveOutOfHibernation(float moveAmount, List<Integer> xPositionIntervall, List<Integer> yPositionIntervall) {
        if (sprite.getY() < 400) {
            sprite.translateY(moveAmount);
        }


        if (!moveToStart)
        {
            if (!xPositionIntervall.contains(740) || !yPositionIntervall.contains(400))
            {
                if (sprite.getX() < 740 && yPositionIntervall.contains(400))
                    sprite.translateX(moveAmount);
                else if (sprite.getX() > 740 && yPositionIntervall.contains(400))
                    sprite.translateX(-moveAmount);
            }
            else if (xPositionIntervall.contains(740) && sprite.getY() >= 400) {
                moveToStart = true;
            }
        }

        else {
            sprite.translateY(moveAmount);
            if (xPositionIntervall.contains(740) && yPositionIntervall.contains(550))
            {
                alive = true;
                ghostsInGrave--;
            }
        }
    }


    private void markNodes() {
        //NodeTreeSingleton.setNodesToMark(shortestPath);
        //NodeTreeSingelton.setNodesToMark(path);
    }


    private void dieIfPacmanStandsOnGhost(ArrayList<Integer> xPositionIntervall, ArrayList<Integer> yPositionIntervall) {
        // Dø hvis pacman går på spøkelse
        int pacmanPosX = (int) Pacman.getInstance().getSprite().getX();
        int pacmanPosY = (int) Pacman.getInstance().getSprite().getY();
        if (xPositionIntervall.contains(pacmanPosX) && yPositionIntervall.contains(pacmanPosY)) {
            WindowSingleton.getInstance().removeGhostAndAddNewGhost(this);
        }
    }

    private List<Node> makeRunAwayPath(ArrayList<Integer> xPositionIntervall, ArrayList<Integer> yPositionIntervall) {
        // Finn en vei å løpe
        if (toNode == null || (xPositionIntervall.contains((int) toNode.getPosX()) && yPositionIntervall.contains((int) toNode.getPosY()) ) ) {

            if (toNode != null)
                currentNode = toNode;


            int halfwayX = 700;
            int halfwayY = 550;

            float positionX = Pacman.getInstance().getSprite().getX();
            float positionY = Pacman.getInstance().getSprite().getY();


            if (positionX < halfwayX && positionY < halfwayY)
                return Dijkstras.findLongestPath(currentNode, NodeTreeSingelton.getInstance().getNodeTopRight());
            else if (positionX > halfwayX && positionY < halfwayY)
                return Dijkstras.findLongestPath(currentNode, NodeTreeSingelton.getInstance().getNodeTopLeft());
            else if (positionX < halfwayX && positionY > halfwayY)
                return Dijkstras.findLongestPath(currentNode, NodeTreeSingelton.getInstance().getNodeBottomRight());
            else
                return Dijkstras.findLongestPath(currentNode, NodeTreeSingelton.getInstance().getNodeBottomLeft());


        }

        return null;
    }

    private List<Node> findPathHunt(ArrayList<Integer> xPositionIntervall, ArrayList<Integer> yPositionIntervall) {

        nodeMovingToHit = false;

        //når er toNode = null? Helt på starten, før man har nådd toNode for første gang

        if (toNode == null //hvis tilnoden er null, eller man står på pacman sin tilnode
                || xPositionIntervall.contains((int) toNode.getPosX()) && yPositionIntervall.contains((int) toNode.getPosY())) {

            //hvis tilnoden ikke er null, og man står på pacman sin tilnode
            if (toNode != null && (xPositionIntervall.contains((int) toNode.getPosX()) && yPositionIntervall.contains((int) toNode.getPosY())))
                currentNode = toNode;

            int i = 0;
            if (Pacman.getInstance().toNode == null) //hvis tilnoden er null, hvilket den er på starten
                return Dijkstras.findShortestPath(currentNode, Pacman.getInstance().fromNode);



                //eller - tilnoden ikke er null, og man står på Pacman sin tilNode
            else if (toNode != null && (xPositionIntervall.contains((int) Pacman.getInstance().toNode.getPosX()) && yPositionIntervall.contains((int) Pacman.getInstance().toNode.getPosY()))) {
                return Dijkstras.findShortestPath(currentNode, Pacman.getInstance().fromNode);
            }

            else //hvis man ikke står på Pacman sin tilNode
                return Dijkstras.findShortestPath(currentNode, Pacman.getInstance().toNode);


        }

        return null;
    }


    private void stopGameIfGhostEatsPacman(ArrayList<Integer> xPositionIntervall, ArrayList<Integer> yPositionIntervall) {
        int pacmanPosX = (int) Pacman.getInstance().getSprite().getX();
        int pacmanPosY = (int) Pacman.getInstance().getSprite().getY();

        if (xPositionIntervall.contains(pacmanPosX) && yPositionIntervall.contains(pacmanPosY))
            WindowSingleton.getInstance().stopGame();
    }


    private void setToNodeFromPath(List<Node> path) {
        if (path != null) //
            for (Map.Entry<Node, Integer> neighbour : currentNode.getNeighbours().entrySet()) { //ser på hver nabo
                if (path.size() >= 2 && neighbour.getKey() == path.get(path.size() - 2)) { //hvis noder i path er mer enn 1, naboen man ser på er den samme som den siste noden i path
                    toNode = neighbour.getKey(); //sett toNode til naboen
                    break;
                }
            }
    }


    //UP: 0, RIGHT: 1, DOWN: 2, LEFT: 3
    private void moveInDirection() {
        if (toNode != null) {
            if (toNode == currentNode.getLeft()) {
                sprite.translateX(-moveAmount);
                moveDirection = 3;
                sprite.setFlip(true, false);
            }
            else if (toNode == currentNode.getRight()) {
                sprite.translateX(moveAmount);
                moveDirection = 1;
                sprite.setFlip(false, false);
            }
            else if (toNode == currentNode.getUp()) {
                sprite.translateY(moveAmount);
                moveDirection = 0;
            }
            else if (toNode == currentNode.getDown()) {
                sprite.translateY(-moveAmount);
                moveDirection = 2;
            }
        }

    }


}



