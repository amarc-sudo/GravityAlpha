package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.scoreboard.ScoreBoard;
import com.runespace.game.utils.Constants;

public class Level3 extends Level {

	ScoreBoard scoreArray;
	public Level3(GameStateManager gsm, Vector2 gravity) {
		super(gsm, gravity);
		tiledMap = LaunchGame.assetManager.get("maps/Main.tmx", TiledMap.class);
		scoreArray = new ScoreBoard();
		scoreArray.load("level1");

		createGround();
		createDead();
		//createTiles(LaunchGame.assetManager.get("maps/Main.tmx", TiledMap.class));
		//this.createTileCondition(Constants.SPHERE_BIT, "dead", true);
		// TODO Auto-generated constructor stub
		this.createPlayer(400, 1000);
		create();
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		super.update(dt);
		if(!gameOverBool) {
			moveCam(5);
		}
		handleInput();
		checkGameOver();
		if(gameOverBool) {
			Gdx.input.setInputProcessor(screenGameOver.Stage());
			if(this.screenGameOver.menuPressed()) {
				this.gameOver();
			}
			if(this.screenGameOver.retryPressed() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))	{
				scoreArray.add(this.score);
				scoreArray.save("level1");
				gsm.set(new Level3(gsm, Constants.GRAVITY_WORLD));
			}
		}
	}
	

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		super.render(sb);
			sb.begin();
			player.render(sb, false);
			sb.end();
			debug.render(world, box2dCam.combined);
			hud.stage.draw();
		if(gameOverBool) {
			screenGameOver.Stage().draw();
		}

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	@Override
	public void gameOver() {
		scoreArray.add(this.score);
		scoreArray.save("level1");
		super.gameOver();
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		super.handleInput();
	}

	public void create() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
