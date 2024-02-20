package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Food.FoodHashlistSingleton;
import com.mygdx.game.Food.FoodUtils;
import com.mygdx.game.Map.MapSingleton;
import com.mygdx.game.NodeTree.NodeTreeSingelton;
import com.mygdx.game.Organism.Ghost;
import com.mygdx.game.Organism.Pacman;

import java.util.ArrayList;

import static com.mygdx.game.Organism.Ghost.ghostsInGrave;

public class WindowSingleton extends ApplicationAdapter {

	private SpriteBatch batch;
	public ArrayList<Ghost> ghosts = new ArrayList<>();
	private static WindowSingleton instance;
	private MapSingleton map;
	private NodeTreeSingelton nodeTree;

	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}

	public void setGhosts(ArrayList<Ghost> ghosts) {
		this.ghosts = ghosts;
	}

	private WindowSingleton() {}
	public static WindowSingleton getInstance() {
		if (instance == null)
			instance = new WindowSingleton();
		return instance;
	}




	public SpriteBatch getBatch() {
		return batch;
	}


	public void removeGhostAndAddNewGhost(Ghost ghostToRemove) {
		for (int i = 0; i < ghosts.size(); i++)
			if (ghosts.get(i) == ghostToRemove) {
				ghosts.set(i, new Ghost(false));
			}
	}




	@Override
	public void create() {
		batch = new SpriteBatch();

		nodeTree = NodeTreeSingelton.getInstance();
		nodeTree.makeNodeTree();

		map = MapSingleton.getInstance();
		map.createMap();

		ghosts.add(new Ghost());
		ghosts.add(new Ghost());
		ghosts.add(new Ghost());
		ghosts.add(new Ghost());




		FoodUtils.placeFoods();
		FoodHashlistSingleton.getInstance().addAllFoods( FoodUtils.getFoods() );









	}


	boolean gameIsStopped = false;
	public void stopGame() {
		gameIsStopped = true;
	}




	@Override
	public void render() {
		batch.begin();

		ScreenUtils.clear(1, 0, 0, 1);
		map.drawMap();


		//spill stopper hvis: pacman er død, eller pacman har vunnet
		if (!gameIsStopped) {

			Pacman.getInstance().handleMovement();

			for (Ghost ghost : ghosts) {
				if (ghost != null && !ghost.isHibernatingInGrave())
					ghost.handleMovement();
			}

		}

		Pacman.getInstance().drawOrganism();

		for (Ghost ghost : ghosts)
			if (ghost != null)
				ghost.drawOrganism();

		FoodUtils.drawFoods();


		//nodeTree.drawNodes(); //make the nodes visible

		//System.out.println(FoodUtils.getFoodSize());


		batch.end();
	}


	@Override
	public void dispose() {
		batch.dispose();
		for (Ghost ghost : ghosts) {
			ghost.getTexture().dispose();
		}

		Pacman.getInstance().getTexture().dispose();


	}
}
