package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.scoreboard.ScoreBoard;
import com.runespace.game.utils.Constants;

public class Level4 extends Level{

	ScoreBoard scoreArray;

	public Level4(GameStateManager gsm, Vector2 gravity) {
		super(gsm, gravity);
		scoreArray = new ScoreBoard();
		scoreArray.load("level2");
	//
        LaunchGame.assetManager.unload("maps/sans.tmx");
        LaunchGame.assetManager.setLoader(TiledMap.class,  new TmxMapLoader(new InternalFileHandleResolver()));
        LaunchGame.assetManager.load("maps/sans.tmx", TiledMap.class);
        while(!LaunchGame.assetManager.update());
		tiledMap = LaunchGame.assetManager.get("maps/sans.tmx");
		createGround();
		createDead();
		createSensorG();

	//	this.createTileCondition(Constants.SPHERE_BIT, "sensorG", true);
	//	this.createTileCondition(Constants.SPHERE_BIT, "deadInversed", true);
	//	this.createTileCondition(Constants.SPHERE_BIT, "dead", true);
		this.createPlayer(400,1700);
		createCoin();
		//this.cam.position.x =0;
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		super.update(dt);
		moveCam(4);
		handleInput();
		checkGameOver();
		gravityChange();
		if(gameOverBool) {
			Gdx.input.setInputProcessor(screenGameOver.Stage());
			if(this.screenGameOver.menuPressed()) {
				this.gameOver();
			}
			if(this.screenGameOver.retryPressed())	{
				scoreArray.add(this.score);
				scoreArray.save("level2");
				gsm.set(new Level4(gsm, Constants.GRAVITY_WORLD));
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		super.render(sb);

		
		sb.begin();

		if(gravityBool)
			player.render(sb, gravityBool);
		if(!gravityBool)
			player.render(sb, gravityBool);
		sb.end();
		if(gameOverBool) {
			screenGameOver.Stage().draw();
		}
		if(win){

		}
		hud.stage.draw();
		debug.render(world, box2dCam.combined);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
/*
	@Override
	public void createTiles(TiledMap tiledMap) {
		// TODO Auto-generated method stub
		super.createTiles(tiledMap);
	}

	@Override
	public void createTiledBodies(TiledMapTileLayer layer, short BITS) {
		// TODO Auto-generated method stub
		super.createTiledBodies(layer, BITS);
	}*/

	@Override
	public void createTileCondition(short BIT, String type, Boolean fixture) {
		// TODO Auto-generated method stub
		super.createTileCondition(BIT, type, fixture);
	}

	@Override
	public void createPlayer(int x, int y) {
		// TODO Auto-generated method stub
		super.createPlayer(x, y);
	}



	@Override
	public void moveCam(int speedCam) {
		// TODO Auto-generated method stub
		super.moveCam(speedCam);
	}

	@Override
	public void checkGameOver() {
		// TODO Auto-generated method stub
		super.checkGameOver();
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
        score = hud.score;
		scoreArray.add(this.score);
		scoreArray.save("level2");
		super.gameOver();
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		super.handleInput();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	

}
